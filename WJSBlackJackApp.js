/*
Built a BlackJack app using React.js
*/

import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import { Button, Input, Typography } from "antd";


class App extends Component {
  
  state = {
    deck : [],
    phand : [],
    dhand :[],
    pvalue : 0,
    dvalue : 0,
    pwins : 0,
    dwins : 0,
    index : 0,
    shuffleapi : "https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=20",
    drawapi1 : "https://deckofcardsapi.com/api/deck/",
    drawapi2 : "/draw/?count=52",
    testapi : "https://deckofcardsapi.com/api/deck/71zw1u3g22po/draw/?count=52",
    playing : false,
    dbust: false
  }     
  componentDidMount () {
    fetch(this.state.shuffleapi).then(response => response.json())
    .then(shuffled =>{
      let deckID = shuffled.deck_id
      console.log(deckID)
      fetch("https://deckofcardsapi.com/api/deck/"+ deckID + "/draw/?count=52")
      .then(response => response.json())
      .then(
        res => {
          this.setState({   
            deck: res.cards,
          })
        }
      )})
  
      }

  assignvalues = (deck) => {
    let n = deck.length
    for (let i=0; i<n; i++) {
      let c = deck[i].value 
      if (c === "KING" || c === "QUEEN" || c=== "JACK" ) {
        c = '10'
       
        }
      else if (c === "ACE") {
        c = '11'

      }
      deck[i].value = Number(c)
      }
      
    return deck
    }
  

  
  openingdeal = () => {
    this.assignvalues (this.state.deck)
    let pv = this.state.deck[this.state.index].value + this.state.deck[this.state.index+1].value
    this.setState({
      phand : [this.state.deck[this.state.index],this.state.deck[this.state.index+1]],  
      dhand : [this.state.deck[this.state.index+2]],
      index : this.state.index + 4,
      playing : true, 
      dvalue : this.state.deck[this.state.index+2].value,
    })
    if (pv === 22) {
      this.setState({
        pvalue : 12
      })
    }
    else {
      this.setState({
        pvalue: pv
      })
    }    
  }
  loser = (bust)=>{
    if (bust) {
      alert ("You Busted! The House Always Wins")
    }
    else {
      alert("Dealers Hand > Yours! You lose" )
    }
    this.setState({
      dwins : this.state.dwins + 1,
      phand : [],
      dhand : [],
      pvalue : 0,
      dvalue : 0,
      playing: false,
      dbust: false
    })
  
    this.openingdeal()

  }
  
  push = () => {
    alert("No Blood")
    this.setState({
      phand : [],
      dhand : [],
      pvalue : 0,
      dvalue : 0,
      playing : false,
      dbust: false
    })
    this.openingdeal()
  }

  winnerwinner =(dbust)=>{
    if (dbust) {
      alert ("Dealer Busted! You Win")
    }
    else {
      alert("Your Hand > Dealers! You Win")
    }
    this.setState({
      pwins : this.state.pwins + 1,
      phand : [], 
      dhand : [],
      pvalue : 0,
      dvalue : 0,
      playing: false,
      dbust: false
    })

    this.openingdeal()

  }
  

  hit = () => {
    this.assignvalues (this.state.deck)
    let index = this.state.index
    let newcard = this.state.deck[index]
    let newcardv = newcard.value 
    if (this.state.pvalue > 10 && newcardv === 11) {
      newcardv = 1
    }
    this.setState({
      phand : this.state.phand.concat(newcard),
      index : index + 1
    })
    let n = this.state.phand.length

    let pv = this.state.pvalue + newcardv
    let bust = true
    if (pv > 21) {
      this.loser(bust)
    }
    else
    this.setState({
      pvalue : pv
      })
  }

  stand = () => {
    let index = this.state.index
    let dvalue = this.state.dvalue
    let pvalue = this.state.pvalue
    let dhand = this.state.dhand 
    let counter = 1
    while (dvalue < 17) {
        this.setState({ 
          dhand : dhand.concat(this.state.deck[index + counter]),
          dvalue: dvalue += this.state.deck[index + counter].value
         })
         counter += 1
    } 
    if (dvalue > 21) {
      let dbust = true
      this.winnerwinner(dbust)
    }
    else if (dvalue < pvalue) {
      this.winnerwinner()
    }
    else if (dvalue > pvalue) {
      this.loser()
    }
  
    else 
     this.push()

    this.setState({
      index : index + (counter - 1)
    })


}

  
  render() {
    console.log(this.state)
    return (
      <div className ="App">
        <div>
          Player Value: {this.state.playing && this.state.pvalue} 
        </div>
        <div style = {{padding: 10}}>
          Player Wins: {this.state.playing && this.state.pwins}
        </div>

        <div>
          Player Cards
          {this.state.playing &&  <img src = {this.state.phand[0].image}/> }
          {this.state.playing && <img src = {this.state.phand[1].image}/>}
          {this.state.playing &&  this.state.phand[2] && <img src = {this.state.phand[2].image}/>}
          {this.state.playing &&  this.state.phand[3] && <img src = {this.state.phand[3].image}/>}
          {this.state.playing &&  this.state.phand[4] && <img src = {this.state.phand[4].image}/>}
          {this.state.playing &&  this.state.phand[5] && <img src = {this.state.phand[5].image}/>}
          {this.state.playing &&  this.state.phand[6] && <img src = {this.state.phand[6].image}/>}
          {this.state.playing &&  this.state.phand[7] && <img src = {this.state.phand[7].image}/>}
          {this.state.playing &&  this.state.phand[8] && <img src = {this.state.phand[8].image}/>}
          {this.state.playing &&  this.state.phand[9] && <img src = {this.state.phand[9].image}/>}
          {this.state.playing &&  this.state.phand[10] && <img src = {this.state.phand[10].image}/>}
        </div>
        
        <div>

          {!this.state.playing && <Button type ="primary" onClick = {()=> {this.openingdeal()}}> Start Game </Button>}
        </div>

        <div>
          {this.state.playing && <Button type ="primary" size = "large" shape = "round" onClick = {()=> {this.hit()}}> Hit </Button>}
        </div>

        <div style = {{padding: 1}}>
          {this.state.playing && <Button type ="primary" size = "large" shape = "round" onClick = {()=> {this.stand()}}> Stand </Button>} 
        </div>

        <div>
          Dealer Cards
          {this.state.playing && <img src = {this.state.dhand[0].image}/>}
          {this.state.playing &&  this.state.dhand[1] && <img src = {this.state.dhand[1].image}/>}
          {this.state.playing &&  this.state.dhand[2] && <img src = {this.state.dhand[2].image}/>}
          {this.state.playing &&  this.state.dhand[3] && <img src = {this.state.dhand[3].image}/>}
          {this.state.playing &&  this.state.dhand[4] && <img src = {this.state.dhand[4].image}/>}
          {this.state.playing &&  this.state.dhand[5] && <img src = {this.state.dhand[5].image}/>}
          {this.state.playing &&  this.state.dhand[6] && <img src = {this.state.dhand[6].image}/>}
          {this.state.playing &&  this.state.dhand[7] && <img src = {this.state.dhand[7].image}/>}
          {this.state.playing &&  this.state.dhand[8] && <img src = {this.state.dhand[8].image}/>}
          {this.state.playing &&  this.state.dhand[9] && <img src = {this.state.dhand[9].image}/>}
          {this.state.playing &&  this.state.dhand[10] && <img src = {this.state.dhand[10].image}/>}
        </div>
        <div>
          Dealer Value: {this.state.playing && this.state.dvalue}
        </div>
        <div style ={{padding: 10}}>
          Dealer Wins: {this.state.playing && this.state.dwins}
        </div>
      </div>  
    );
  }
}


export default App;


