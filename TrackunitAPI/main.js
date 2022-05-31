var connector = {};


function getAuthType() {
  var response = {type: 'NONE'};
  return response;
}



function getConfig(request) {
  var cc = DataStudioApp.createCommunityConnector();
  var config = cc.getConfig();
  var configParams = request.configParams
  var isFirstRequest = configParams === undefined;
  var optionOne = config.newOptionBuilder()
  .setLabel('unit')
  .setValue('unit')
  var optionTwo = config.newOptionBuilder()
  .setLabel('unitHistory')
  .setValue('unitHistory')
  
  
  if (isFirstRequest) { 
    config.setIsSteppedConfig(true);
    config
    .newSelectSingle()
    .setId('dbName')
    .setName('dbName')
    .setHelpText("Enter Name of Database:")
    .addOption(optionOne)
    .addOption(optionTwo);
    
    config
    .newInfo()
    .setId('INFO')
    .setText(
      'Please enter your credentials'
    );
  }
  
  else {
    config
    .newTextInput()
    .setId('KEY')
    .setName('KEY')
    .setPlaceholder("Enter key:");
    
    if (configParams.dbName == 'unitHistory')  {
      config
      .newTextInput()
      .setId('UnitID')
      .setName('UnitID')
      .setPlaceholder("Enter UnitID:");
      config
      .newTextInput()
      .setId('startDate')
      .setName('startDate')
      .setPlaceholder("Enter Start Date. Ex. 2020-06-21T00:00:00Z");
      config
      .newTextInput()
      .setId('endDate')
      .setName('endDate')
      .setPlaceholder("Enter End Date. Ex. 2020-06-27 00:00:00");
      
      config.setDateRangeRequired(true);
      
      
    }
    
  }
   
  
  
 

  return config.build();
    
   }
  


function getFields(dbName) {
  var cc = DataStudioApp.createCommunityConnector();
  var fields = cc.getFields();
  var types = cc.FieldType;
  var aggregations = cc.AggregationType;
  
  if (dbName == 'unit') {
    fields.newDimension()
    .setId('id')
    .setName('id')
    .setType(types.TEXT);
    
    fields.newDimension()
    .setId('serialNumber')
    .setName('serialNumber')
    .setType(types.TEXT);
    
    fields.newDimension()
    .setId('phoneNumber')
    .setName('phoneNumber')
    .setType(types.TEXT);  
    
    fields.newDimension()
    .setId('vehicleType')
    .setName('vehicleType')
    .setType(types.TEXT);
    
    fields.newDimension()
    .setId('deviceType')
    .setName('deviceType')
    .setType(types.TEXT);
    
    fields.newDimension()
    .setId('deviceModel')
    .setName('deviceModel')
    .setType(types.TEXT);
    
    fields.newDimension()
    .setId('name')
    .setName('name')
    .setType(types.TEXT);  
    
    fields.newDimension()
    .setId('displayName')
    .setName('displayName')
    .setType(types.TEXT);
    
    fields.newDimension()
    .setId('referenceNumber')
    .setName('referenceNumber')
    .setType(types.TEXT);
    
    fields.newDimension()
    .setId('note')
    .setName('note')
    .setType(types.TEXT); 
    
    fields.newDimension()
    .setId('createdAt')
    .setName('createdAt')
    .setType(types.YEAR_MONTH_DAY_SECOND);  //Check this
    
    fields.newDimension()
    .setId('input1Name')
    .setName('input1Name')
    .setType(types.TEXT);
    
    fields.newDimension()
    .setId('input2Name')
    .setName('input2Name')
    .setType(types.TEXT);  
    
    fields.newDimension()
    .setId('input3Name')
    .setName('input3Name')
    .setType(types.TEXT);  
    
    fields.newDimension()
    .setId('input4Name')
    .setName('input4Name')
    .setType(types.TEXT);  
    
    fields.newDimension()
    .setId('categoryId')
    .setName('categoryId')
    .setType(types.TEXT);  
    
    fields.newDimension()
    .setId('clientId')
    .setName('clientId')
    .setType(types.TEXT); 
    
    fields.newDimension()
    .setId('keyId')
    .setName('keyId')
    .setType(types.TEXT);
    
    fields.newDimension()
    .setId('gpsFixTime')
    .setName('gpsFixTime')
    .setType(types.YEAR_MONTH_DAY_SECOND); 
    
    fields.newDimension()
    .setId('messageTime')
    .setName('messageTime')
    .setType(types.YEAR_MONTH_DAY_SECOND);  
    
    fields.newDimension()
    .setId('location')
    .setName('location')
    .setType(types.LATITUDE_LONGITUDE);
    
    fields.newMetric()
    .setId('altitude')
    .setName('altitude')
    .setType(types.NUMBER);  
    
    fields.newMetric()
    .setId('heading')
    .setName('heading')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('speed')
    .setName('speed')
    .setType(types.NUMBER);
    
    fields.newDimension()       
    .setId('address.city')
    .setName('address.city')
    .setType(types.TEXT);
    
    fields.newDimension()       
    .setId('address.country')
    .setName('address.country')
    .setType(types.TEXT);
    
    fields.newDimension()       
    .setId('address.streetAddress')
    .setName('address.streetAddress')
    .setType(types.TEXT);
    
    fields.newDimension()       
    .setId('address.zipCode')
    .setName('address.zipCode')
    .setType(types.TEXT);
    
    fields.newMetric()
    .setId('temperature1')
    .setName('temperature1')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('temperature2')
    .setName('temperature2')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('gsmLevel')
    .setName('gsmLevel')
    .setType(types.NUMBER);
    
    fields.newDimension()
    .setId('gsmSignalQuality')
    .setName('gsmSignalQuality')
    .setType(types.TEXT);
    
    fields.newMetric()
    .setId('batteryLevel')
    .setName('batteryLevel')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('preRun1')
    .setName('preRun1')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('preRun2')
    .setName('preRun2')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('run1')
    .setName('run1')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('run2')
    .setName('run2')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('run3')
    .setName('run3')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('run4')
    .setName('run4')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('runOdo')
    .setName('runOdo')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('preKm')
    .setName('preKm')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('km')
    .setName('km')
    .setType(types.NUMBER);
    
    fields.newDimension()
    .setId('input1')
    .setName('input1')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('input2')
    .setName('input2')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('input3')
    .setName('input3')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('input4')
    .setName('input4')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('input5')
    .setName('input5')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('input6')
    .setName('input6')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('input7')
    .setName('input7')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('input8')
    .setName('input8')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('input9')
    .setName('input9')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('input10')
    .setName('input10')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('output1')
    .setName('output1')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('output2')
    .setName('output2')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('output3')
    .setName('output3')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('output4')
    .setName('output4')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('output5')
    .setName('output5')
    .setType(types.BOOLEAN);
    
    fields.newMetric()
    .setId('analogInput1')
    .setName('analogInput1')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('analogInput2')
    .setName('analogInput2')
    .setType(types.NUMBER);
    
    
    fields.newMetric()
    .setId('analogInput4')
    .setName('analogInput4')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('input1ChangeCounter')
    .setName('input1ChangeCounter')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('input2ChangeCounter')
    .setName('input2ChangeCounter')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('input3ChangeCounter')
    .setName('input3ChangeCounter')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('input4ChangeCounter')
    .setName('input4ChangeCounter')
    .setType(types.NUMBER);
    
    fields.newDimension()
    .setId('isActive')
    .setName('isActive')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('isOwn')
    .setName('isOwn')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('currentStop')
    .setName('currentStop')
    .setType(types.NUMBER);
    
  }
  
  else {
    fields.newDimension()
      .setId('time')
      .setName('time')
      .setType(types.YEAR_MONTH_DAY_SECOND);
    
    fields.newDimension()
    .setId('event')
    .setName('event')
    .setType(types.NUMBER);
    
    fields.newDimension()
    .setId('keyId')
    .setName('keyId')
    .setType(types.TEXT);
    
    fields.newDimension()
    .setId('location')
    .setName('location')
    .setType(types.LATITUDE_LONGITUDE); 

    fields.newDimension()       
    .setId('address.city')
    .setName('address.city')
    .setType(types.TEXT);
    
    fields.newDimension()       
    .setId('address.country')
    .setName('address.country')
    .setType(types.TEXT);
    
    fields.newDimension()       
    .setId('address.streetAddress')
    .setName('address.streetAddress')
    .setType(types.TEXT);
    
    fields.newDimension()       
    .setId('address.zipCode')
    .setName('address.zipCode')
    .setType(types.TEXT);
    
    fields.newMetric()
    .setId('heading')
    .setName('heading')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('speed')
    .setName('speed')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('Km')
    .setName('Km')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('run1')
    .setName('run1')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('run2')
    .setName('run2')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('run3')
    .setName('run3')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('run4')
    .setName('run4')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('runOdo')
    .setName('runOdo')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('temperature1')
    .setName('temperature1')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('temperature2')
    .setName('temperature2')
    .setType(types.NUMBER);

    fields.newDimension()
    .setId('input1')
    .setName('input1')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('input2')
    .setName('input2')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('input3')
    .setName('input3')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('input4')
    .setName('input4')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('input5')
    .setName('input5')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('input6')
    .setName('input6')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('input7')
    .setName('input7')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('input8')
    .setName('input8')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('input9')
    .setName('input9')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('input10')
    .setName('input10')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('output1')
    .setName('output1')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('output2')
    .setName('output2')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('output3')
    .setName('output3')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('output4')
    .setName('output4')
    .setType(types.BOOLEAN);
    
    fields.newDimension()
    .setId('output5')
    .setName('output5')
    .setType(types.BOOLEAN);
    
    fields.newMetric()
    .setId('analogInput1')
    .setName('analogInput1')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('analogInput2')
    .setName('analogInput2')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('analogInput3')
    .setName('analogInput3')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('analogInput4')
    .setName('analogInput4')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('Input1ChangeCounter')
    .setName('Input1ChangeCounter')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('Input2ChangeCounter')
    .setName('Input2ChangeCounter')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('Input3ChangeCounter')
    .setName('Input3ChangeCounter')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('Input4ChangeCounter')
    .setName('Input4ChangeCounter')
    .setType(types.NUMBER);
    
    fields.newMetric()
    .setId('batteryLevel')
    .setName('batteryLevel')
    .setType(types.NUMBER);
 
    fields.newMetric()
    .setId('externalPower')
    .setName('externalPower')
    .setType(types.NUMBER);


  }

  return fields;
}

/**
 * Returns the schema for the given request.
 *
 * @param {Object} request Schema request parameters.
 * @returns {Object} Schema for the given request.
 */

function getSchema(request) {
  var fields = getFields(request.configParams.dbName).build();
  return {schema: fields};
}


function getData(request) {
 
   var requestedFieldIds = request.fields.map(function(field) {
    return field.name;
  });
  var requestedFields = getFields(request.configParams.dbName).forIds(requestedFieldIds);  
  
  var url = []
  try {
    if (request.configParams.dbName == 'unit') {
      url = ['https://api.trackunit.com/public/Unit?token=',
                 request.configParams.KEY]

      
      }
    else {
      
      url = ['https://api.trackunit.com/public/Report/UnitHistory?token=',
                 request.configParams.KEY, '&UnitId=', request.configParams.UnitID, '&From=', request.configParams.startDate, '&To=', request.configParams.endDate ]
      
    }
    var response = UrlFetchApp.fetch(url.join(''));
  
  
  }
  
  catch (e) {
    DataStudioApp.createCommunityConnector().newUserError()
    .setDebugText("Error Fetching URL. Respones: " + response)
    .setText("Error Fetching URL. Respones: " + response)
    .throwException()
  }
  

  
  try {
    var TUData = JSON.parse(response).list;
    
  }
  catch (e) {
    DataStudioApp.createCommunityConnector().newUserError()
    .setDebugText('Parsing Error. Exception details: ' + e)
    .setText('Parsing Error: ' + e)
    .throwException()
    
  }
  
  try {
  
    var rows = responseToRows(requestedFields, TUData); //Right here
    return {
      schema: requestedFields.build(),
      rows: rows
  };
  }
  catch (e) {
    DataStudioApp.createCommunityConnector().newUserError()
    .setDebugText('Error Formatting Data. Exception details: ' + e)
    .setText('Error Formatting Data: ' + e)
    .throwException()
    
  }
   
  

  
}

function responseToRows(requestedFields, TUData) {
  return TUData.map(function(vehicle) {
    
    var values = [];
    requestedFields.asArray().forEach(function(field) {
      var fieldID = field.getId();
      
      if (fieldID == "location"){
        var vehicleObject = vehicle.location;
        if (vehicleObject != null) {
          var latLong = vehicleObject['latitude'] + ',' + vehicleObject['longitude'];
          values.push(latLong);
        }
        else {
          values.push("Null Location");
        }
      }
      
      else if (fieldID == "address.city" || fieldID == "address.country" || fieldID == "address.streetAddress" || fieldID == "address.zipCode") {
        var addressObject = vehicle["address"];
        if (addressObject != null) {  
          if (fieldID == "address.city") {
            values.push(vehicle['address']['city']);
          }
          else if (fieldID == "address.country") {
            values.push(vehicle['address']['country']);

          }
          else if (fieldID == "address.streetAddress") {
            values.push(vehicle['address']['streetAddress']);
          }
          else if(fieldID == "address.zipCode") {
            values.push(vehicle['address']['zipCode']);
          }
          else {
          }
          
        }
        
        else {
          values.push("Null Address");
         
        }
      }
      
        
      else {
        values.push(vehicle[fieldID]);
        
      }
      
    
      
      
 
    }); 
    

                                    
                                    
  return {values: values};                                    
  
  
});
}

function isAdminUser() {
  return true;
}
                       




