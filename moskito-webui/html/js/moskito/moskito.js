var WEBUI = {};
WEBUI.utils = {};

(function(){	
	
	var Lang = YAHOO.lang,
		Util = YAHOO.util;
	
	WEBUI.utils.Moskito = function(interval, timeUnit){
		this.setCurrentInterval(interval);
		this.setRequestedInterval(interval);
		this.setCurrentTimeUnit(timeUnit);
		this.setRequestedTimeUnit(timeUnit);
		this.initEvents();
		this.initDataSource();
		this.sendDataRequest();
	};
	
	var Moskito = WEBUI.utils.Moskito; 
	
	Lang.augmentObject(Moskito, {
		
		DEFAULT_INTERVAL: 'default',
		DEFAULT_TIME_UNIT: 'MILLISECONDS',
		DEFAULT_CATEGORY: '',
		DEFAULT_SUBSYSTEM: '',
		
		EVENT_ON_DATA_LOAD: 'onDataLoad',
		EVENT_ON_UPDATE: 'onUpdate',
		EVENT_BEFORE_AUTOUPDATE: 'beforeAutoUpdate',
		EVENT_ON_AUTOUPDATE: 'onAutoUpdate',
		EVENT_BEFORE_AUTOUPDATE_STOP: 'beforeAutoUpdateStop',
		EVENT_ON_AUTOUPDATE_STOP: 'onAutoUpdateStop',
	});
	
	Moskito.prototype = {
		_dataSource: null,
		_intervals: [],
		_curInterval: Moskito.DEFAULT_INTERVAL,
		_reqInterval: Moskito.DEFAULT_INTERVAL,
		_timeUnits: [],
		_curTimeUnit: Moskito.DEFAULT_TIME_UNIT,
		_reqTimeUnit: Moskito.DEFAULT_TIME_UNIT,
		_categories: [],
		_curCategory: Moskito.DEFAULT_CATEGORY,
		_reqCategory: Moskito.DEFAULT_CATEGORY,
		_subsystems: [],
		_curSubsystem: Moskito.DEFAULT_SUBSYSTEM,
		_reqSubsystem: Moskito.DEFAULT_SUBSYSTEM,
		_decorators: [],
		
		initEvents: function(){
			this.createEvent(Moskito.EVENT_ON_DATA_LOAD);
			this.createEvent(Moskito.EVENT_ON_UPDATE);
			this.createEvent(Moskito.EVENT_BEFORE_AUTOUPDATE);
			this.createEvent(Moskito.EVENT_ON_AUTOUPDATE);
			this.createEvent(Moskito.EVENT_BEFORE_AUTOUPDATE_STOP);
			this.createEvent(Moskito.EVENT_ON_AUTOUPDATE_STOP);
		},
		
		initDataSource: function(){
			this._dataSource = new YAHOO.util.DataSource('mskShowAllProducersYUI.json?pForward=json');
			this._dataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
			this._dataSource.responseSchema = {
				resultsList : "moskito"
			};

		},
		getIntervals: function(){
			return _intervals;
		},
		getCurrentInterval: function(){
			return _getValue(this._curInterval);
		},
		setCurrentInterval: function(interval){
			this._curInterval = interval;
		},
		getRequestedInterval: function(){
			return _getValue(this._reqInterval);
		},
		setRequestedInterval: function(interval){
			this._reqInterval = interval;
		},
		
		getCurrentTimeUnit: function(){
			return _getValue(this._curTimeUnit);
		},
		setCurrentTimeUnit: function(timeUnit){
			this._curTimeUnit = timeUnit;
		},
		getRequestedTimeUnit: function(){
			return _getValue(this._reqTimeUnit);
		},
		setRequestedTimeUnit: function(timeUnit){
			this._reqTimeUnit = timeUnit;
		},
		
		getCategories: function(){
			return _Categories;
		},
		getCurrentCategory: function(){
			return _getValue(this._curCategory);
		},
		setCurrentCategory: function(Category){
			this._curCategory = Category;
		},
		getRequestedCategory: function(){
			return _getValue(this._reqCategory);
		},
		setRequestedCategory: function(Category){
			this._reqCategory = Category;
		},
		
		getSubsystems: function(){
			return _Subsystems;
		},
		getCurrentSubsystem: function(){
			return _getValue(this._curSubsystem);
		},
		setCurrentSubsystem: function(Subsystem){
			this._curSubsystem = Subsystem;
		},
		getRequestedSubsystem: function(){
			return _getValue(this._reqSubsystem);
		},
		setRequestedSubsystem: function(Subsystem){
			this._reqSubsystem = Subsystem;
		},
		
		getQuery: function(){
			return "&pUnit=" + this.getRequestedTimeUnit() + "&pInterval=" + this.getRequestedInterval() + "&pSubsystem=" + this.getRequestedSubsystem() + "&pCategory=" + this.getRequestedCategory();
		},
		sendDataRequest: function(){
			var callback = {
					success : this.onDataLoad,
					failure : function(oRequest, oResponse, oPayload){
						alert("Moskito requesting failure!");
						},
					scope : this
			};
			this._dataSource.sendRequest(this.getQuery(), callback);
		},
		onDataLoad: function(oRequest, oResponse, oPayload){
			this.writeData(oRequest, oResponse, oPayload);
			this.fireEvent(Moskito.EVENT_ON_DATA_LOAD);
		},
		writeData: function(oRequest, oResponse, oPayload){
			_moskitoData = oResponse.results[0];
			this.setCurrentInterval(_moskitoData.interval);
			this.setCurrentTimeUnit(_moskitoData.timeUnit);
			this.setCurrentCategory(_moskitoData.category);
			this.setCurrentSubsystem(_moskitoData.subsystem);
			
			var decorators = [];
			for(var i = 0; i < _moskitoData.decorators.length; i++){
				decorators[i] = _moskitoData.decorators[i];
			}
			this._decorators = decorators;
		},
		
		getDecorators: function(){
			return this._decorators;
		},
		
		update: function(){
			this.sendDataRequest();
			this.fireEvent(Moskito.EVENT_ON_UPDATE, {
				interval:this.getRequestedInterval(),
				timeUnit:this.getRequestedTimeUnit(),
				category:this.getRequestedCategory(),
				subsystem:this.getRequestedSubsystem()
			});
		},
		
		autoUpdate: function(interval){
			this.fireEvent(Moskito.EVENT_BEFORE_AUTOUPDATE); 
			this.autoUpdateStop();
			 if(this.isValidUpdateInterval(interval)){
				 this._updating = Lang.later(interval,this,function(){
					 this.update();
				 },[],true);
			 }
			 this.fireEvent(Moskito.EVENT_ON_AUTOUPDATE);
		 },
		 autoUpdateStop: function(interval, oReq){
			 this.fireEvent(Moskito.EVENT_BEFORE_AUTOUPDATE_STOP);
			 if(this.isAutoUpdate()){
				 this._updating.cancel();
				 this._updating = false;
			 }
			 this.fireEvent(Moskito.EVENT_ON_AUTOUPDATE_STOP);
		 },
		 isValidUpdateInterval: function(interval){
			 return interval > 0;
		 },
		 isAutoUpdate: function(){
			 if(this._updating == null)
				 this._updating = false;
			 return !(this._updating === false);
		 },
		
	};
	
	var _getValue = function(property){
		return Lang.isFunction(property)? property.apply(property): property;
	}
	
	Lang.augmentProto(Moskito, Util.EventProvider);
	
})();