'use strict';
const http = require('http');
var assert = require('assert');
const express= require('express');
const app = express();
const mustache = require('mustache');
const filesystem = require('fs');
const url = require('url');
const port = Number(process.argv[2]);

const hbase = require('hbase')
var hclient = hbase({ host: process.argv[3], port: Number(process.argv[4])})

const validTeams = new Set(['PHI', 'BAL', 'CLE', 'IND', 'MIA', 'NE', 'NO', 'NYG', 'ARI', 'CAR',
    'GB', 'DET', 'OAK', 'CIN', 'ATL', 'BUF', 'NYJ', 'PIT', 'TB', 'TEN',
    'WAS', 'LA', 'SF', 'DEN', 'JAX', 'DAL', 'CHI', 'HOU', 'KC', 'MIN',
    'SEA', 'LAC', 'LV'])
const validWeeks = new Set(['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18'])
const validYears = new Set(['2018', '2019', '2020'])

app.use(express.static('public'));
app.get('/',function (req, res) {
    res.sendFile(__dirname + '/home.html');
})

function rowToMap(row) {
    var stats = {}
    row.forEach(function (item) {
        stats[item['column']] = Number(item['$'])
    });
    return stats;
}

app.get('/topSpeed',function (req, res) {
    let key = buildHbaseKey(req.query);
    var template = filesystem.readFileSync("topSpeed.mustache").toString();
    console.log(key)
    if (key === "INVALID INPUT"){
        var html = mustache.render(template, {
            topSpeed: key
        });
        res.send(html);
    }

    else{
        //Query From Hbase
        hclient.table("wjsjr_batchServing").row(key).get('speed', function(err, batchRow) {
            hclient.table("wjsjr_speedServing").row(key).get('speed', function(err, speedRow) {
                let speedSpeed = 0
                let batchSpeed = 0
                let speed = 0

                //If Key does not appear in Hbase, team had a bye this week
                if (speedRow != null){
                    const map = rowToMap(speedRow);
                    speedSpeed =  map["speed:s"];
                    console.log("speedspeed", speedSpeed)
                }

                if (batchRow != null){
                    const map = rowToMap(batchRow);
                    batchSpeed =  map["speed:s"];
                    console.log("batchSpeed", batchSpeed)
                }

                if (batchSpeed == 0 && speedSpeed == 0){
                    speed = "Bye Week!"
                }

                else if (speedSpeed == 0){
                    speed = batchSpeed
                }

                else if (batchSpeed == 0){
                    speed = speedSpeed
                }

                else{
                    batchSpeed = parseFloat(batchSpeed)
                    speedSpeed = parseFloat(speedSpeed)
                    speed = Math.max(batchSpeed, speedSpeed)
                }



                //Row should have top speed of fastest player, playerName, teamName
                var html = mustache.render(template, {
                    topSpeed: speed
                });

                res.send(html);

            });

    
        });

        
    }



})

app.listen(port, function() {
    console.log(`Server Alive`);
})

//ALW

function buildHbaseKey(query) {
    const team = query["Team"];
    const week = query["Week"];
    const year = query["Year"]
    if (!validTeams.has(team) | !validWeeks.has(week) | !validYears.has(year)){
        return "INVALID INPUT"
    }

    else{
        return  team + "$" + year + "$" + week;
    }


}