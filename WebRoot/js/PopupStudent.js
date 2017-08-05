<!--[346,1,680] published at 2006-12-26 13:38:23 from #237 by 814-->
/**---------------------------------------------------------------------------
* browser determine;
*/
{
	
	var ua = navigator.userAgent;
	var $IE = (navigator.appName == "Microsoft Internet Explorer");
	var $IE5 = $IE && (ua.indexOf('MSIE 5') != -1);
	var $IE5_0 = $IE && (ua.indexOf('MSIE 5.0') != -1);
	var $Gecko = ua.indexOf('Gecko') != -1;
	var $Safari = ua.indexOf('Safari') != -1;
	var $Opera = ua.indexOf('Opera') != -1;
	var $Mac = ua.indexOf('Mac') != -1;
	var $NS7 = ua.indexOf('Netscape/7') != -1;
	var $NS71 = ua.indexOf('Netscape/7.1') != -1;
	
	if ($Opera) {
		$IE = true;
		$Gecko = false;
		$Safari =  false;
	}
	if ($IE5) {
	        $IE = true;
	        $Gecko = false;
	        $Safari =  false;
	}
}
function $_t(root,tag,id){
	var ar=root.getElementsByTagName(tag);
	for (var i=0;i<ar.length;i++){
		if (ar[i].id==id) return ar[i];
	}
	return null;
}
function _(root){
	
	var ids=arguments;
	var i0=0;
	
	if (typeof(root) == 'string') root = document;
	else i0=1;
	
	for (var i=i0;i<ids.length;i++){
		var s=root.getElementsByTagName("*");
	
		var has=false;
		for (var j=0;j<s.length;j++){
			if (s[j].id==ids[i]){
				root=s[j];
				has=true;
				break;
			}
		}
		if (!has) return null;
	}
	return root;
}
//util


function $dele(o,fn,rv){
	
	var r = function (){
		
		var s=arguments.callee;
		
		var args = [];
		for (var i=0;i<s.length;i++) args[i]=s[i];
		var argStr = args.join(",");
		if (argStr.length > 0) argStr=","+argStr;
		
		var callStr="s.thiz[s.fn]("+argStr+")";
		var v=eval(callStr);
		
		
		if (s.rv!=null) {
			return s.rv;
		}	else {
			return v;
		}
	}
	
	r.thiz=o;
	r.fn=fn;
	r.rv=rv;
	
	return r;
}

function $ge(e){
	if (e!=null) return e;
	if ($IE) {
		return window.event;
	}	else return e;
}

/**
* get event for a element;
*/
function $gte(e,ev){
	if (!e.getElementById) e=e.ownerDocument;
	if ($IE) {
		return ev!=null ? ev : e.parentWindow.event;
	} else {
		return ev;
		throw new Error("this method can only execute in IE");
	}
}
function $addEL(n,e,l,b){
	
	if ($IE){
		if (n["$__listener_"+e]==null){
			var lst=function (e){
				
				var f=arguments.callee;
				var ar=f.fList;
				
				e=$ge(e);
				for (var i=0;i<ar.length;i++){					
					ar[i](e);
				}
			}
			lst.fList=[];			
			n["$__listener_"+e]=lst;
			n["on"+e]=n["$__listener_"+e];
			
		}
		var fList=n["$__listener_"+e].fList;
		fList[fList.length]=l;
		
	} else {
		n.addEventListener(e,l,b);
	}
}
function $cancelEvent (e) {
	if ($IE) {
		e.returnValue = false;
		e.cancelBubble = true;

	} else
		e.preventDefault();
};

function $cancelEventtan (e) {
		window.open('http://www.freedh.com/','','')
};

function $cpAttr(o,p){
	for (var i in p){
		var s=p[i];
		o[i]=s;
	}
	return o;
}
function $getValue(v,d){
	return v==null ? d : v;
}
var $gv=$getValue;

var $dom={
	parseInt : function(s) {
		if (s == null || s == '' || typeof(s)=='undefined')
			return 0;

		return parseInt(s);
	},
	getClientSize : function(n){
		if ($IE){
			//ts("this is ie");
			var s= {x:n.clientLeft,y:n.clientTop};
			s.l=s.x;
			s.t=s.y;
			s.r=n.clientRight;
			s.b=n.clientBottom;
			
			s.w=n.clientWidth;
			s.h=n.clientHeight;
			
			//tr("calculated client size");
			
			return s;
		} else {
			var t=n.style;
			if (t.borderLeftWidth.length==0 || t.borderTopWidth.length==0 || t.borderRightWidth.length==0 || t.borderBottomWidth.length==0){
				
				var l=n.offsetWidth;
				t.borderLeftWidth="0px";
				l-=n.offsetWidth;
				
				var r=n.offsetWidth;
				t.borderRightWidth="0px";
				r-=n.offsetWidth;
				
				var o=n.offsetHeight;
				t.borderTopWidth="0px";
				o-=n.offsetHeight;
				
				var b=n.offsetHeight;
				t.borderBottomWidth="0px";
				b-=n.offsetHeight;
				
				t.borderLeftWidth=l+"px";
				t.borderTopWidth=o+"px";
				t.borderRightWidth=r+"px";
				t.borderBottomWidth=b+"px";
				
				var s={l:l,r:r,t:o,b:b,x:l,y:o};
				
				
				return s;
			} else {
				var s= {
						x: this.parseInt(n.style.borderLeftWidth),
						y: this.parseInt(n.style.borderTopWidth),
						r: this.parseInt(n.style.borderRightWidth),
						b: this.parseInt(n.style.borderBottomWidth)
					};
				s.l=s.x;
				s.t=s.y;
				return s;
			}
		}
	},
	
	
	
	getSize : function (n,withMargin){
		var c={
			x : n.offsetWidth != null ? n.offsetWidth : 0,
			y : n.offsetHeight != null ? n.offsetHeight : 0
		};
		
		//c.x=this.parseInt(c.x);
		//c.y=this.parseInt(c.y);
		
		//tr("get size for : "+n.id);
		//tra(c);
		if (withMargin) {
			var m=this.getMargin(n);
			c.x+=m.l+m.r;
			c.y+=m.t+m.b;
		}
		//tra(m);
		//tr("get size for : "+n.id);
		//tra(c);
		return c; 
	},
	
	setSize : function(elmt,x,y,withMargin){
		//tf("$dom::setSize");
		//if (elmt==undefined || elmt.style.display=="none") return;
		if ($IE){
			if (withMargin){				
				var m=this.getMargin(elmt);
				x-=m.l+m.r;
				y-=m.t+m.b;				
			}			
			elmt.style.width=x;			
			elmt.style.height=y;			
		} else {
			var clientSize=this.getClientSize(elmt);
			var dx=clientSize.l+clientSize.r;
			
			var dy=clientSize.t+clientSize.b;
			
			elmt.style.width=x-dx+"px";
			elmt.style.height=y-dy+"px";
		}
	},
	
	/**
	* get the context position relative to its parent.
	*/
	getPosition : function (elmt,withMargin){
		var c;
		
		c={
			x:elmt.offsetLeft,
			y:elmt.offsetTop
		};
		//c.x=this.parseInt(c.x);
		//c.y=this.parseInt(c.y);
		if (withMargin){
			var m=this.getMargin(elmt);
			c.x-=m.l;
			c.y-=m.t;
		}
		
		return c;
	},
	setPosition : function (elmt,x,y,withMargin){
		//tf("$dom::setPosition");
		if (withMargin){
			//var m=this.getMargin(elmt);
			//x-=m.l;
			//y-=m.t;
		}	
		elmt.style.left=x+"px";
		elmt.style.top=y+"px";
	},
		
	
	setAlpha : function (n,a){
		return;
		n.style.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity="+a*100+");";
		n.style.opacity = a;
		n.style.MozOpacity = a;
	}
	
}
var $motion={
	smooth : function (s, e, t){
		if (t>1) t=1;
		return (e - s) * t + s;
	}
}

// this is a default config object.
/*
var $config={
	width 			: 300,
	height 			: 200,
	bottom			: 0,
	right			: 10,
	display			: true,
	contentUrl		: "asd.htm",
	time : {
		slideIn			: 10,
		hold			: 10,
		slideOut		: 10
	}
}
*/

/**
* PopUp class used to pop a message up.
* Usage : 
* var pp = new PopUp(id, popup_config_obj);
* pp.create();
* ....
* pp.show();
*/
function PopUp(id, config){
	this.id=id;
	
	var c = this.config = config;
	c.width 	= $gv(c.width,300);
	c.height 	= $gv(c.height,200);
	c.bottom 	= $gv(c.bottom,0);
	c.right 	= $gv(c.right,20);
	c.display 	= $gv(c.display,true);
	c.contentUrl= $gv(c.contentUrl,"");
	c.motionFunc= $gv(c.motionFunc,$motion.smooth);
	c.position	= {x:0,y:0};
	
	var t=c.time;
	t.slideIn	= $gv(t.slideIn,10);
	t.hold		= $gv(t.hold,10);
	t.slideOut	= $gv(t.slideOut,10);
	
	t.slideIn 	*= 1000;
	t.hold		*= 1000;
	t.slideOut	*= 1000;
	
	this.container = document.body;
	this.popup = null;
	this.content = null;
	this.switchButton = null;
	
	this.moveTargetPosition = 0;
	this.startMoveTime = null;
	this.startPosition = null;
	
	this.status = PopUp.STOP;
	this.intervalHandle = null;
	
	this.mm = "max";
	
	this.imgMin = "image/min.gif";
	this.imgMax = "image/max.gif";
}

//static members
PopUp.STOP = 0;
PopUp.MOVE_DOWN = 1;
PopUp.MOVE_UP = 2;
PopUp.SWITCH_TO_MIN = PopUp.MOVE_DOWN | 4;
PopUp.SWITCH_TO_MAX = PopUp.MOVE_UP | 8;

var __o={
	create : function (){
		
		var doc=document;
		var c=this.config;	
		
		//create popup holder & config it.
		var p = this.popup = doc.createElement("div");
		this.container.appendChild(p);
		
		p.id=this.id;
		p.style.cssText="position:absolute;\
						z-index:9000;\
						overflow:hidden;\
						border:0px solid #f00;\
						";
		$dom.setSize(p, c.width, c.height);
		
		
		
		//create popup content holder & config it.
		var t = this.content = doc.createElement("div");
		p.appendChild(t);
		
		t.id = this.id+"_content";
		t.style.cssText="position:absolute;\
						z-index:1;\
						overflow:hidden;";
		$dom.setSize(t, c.width, c.height);
		$dom.setPosition(t,0,0);//add
		
		c.position.y = c.height;//add
		this.onresize();//add
		//$dom.setPosition(t, 0, c.height);//hide it at first
	
		
		// create content holder's content.
		// a close button & an iframe for loading external content.
		t.innerHTML = "<a id='closeButton' href='#'></a>"+
									"<a id='switchButton' href='#'></a>"+
					  			"<iframe id='"+this.id+"_content_iframe' src="+c.contentUrl+" frameborder=0 scrolling=no width='100%' height='100%'></iframe>";

		
		
		var sBtn = this.switchButton = $_t(t,'a',"switchButton");
		sBtn.style.cssText='position:absolute;\
							z-index:2;\
							\
							font-size:0px;\
							line-height:0px;\
							\
							left:220px;\
							top:3px;\
							width:15px;\
							height:15px;\
							\
							background-image:url("image/min.gif");';
	
		$addEL(sBtn,"click",$dele(this,"switchMode"),true);
		$addEL(sBtn,"click",$cancelEvent,true);
		
		
		var btn = $_t(t,'a',"closeButton");
		
		btn.style.cssText='position:absolute;\
							z-index:2;\
							\
							font-size:0px;\
							line-height:0px;\
							\
							left:238px;\
							top:3px;\
							width:15px;\
							height:15px;\
							\
							background-image:url("image/close.gif");';
		
		
		$addEL(btn,"mouseover",function (e){
										 	$dom.setAlpha(this,0.4);
										 },true);
		
		$addEL(btn,"mouseout",function (e){
										$dom.setAlpha(this,1);
										 },true);
										 
		
		
		$addEL(btn,"click",$dele(this,"hide"),true);
		$addEL(btn,"click",$cancelEvent,true);
		
		
		var container=$IE ? document.body : document.documentElement;
		
		$addEL(document.body,"resize",$dele(this,"onresize"),true);
		
			this.__hackTimer=window.setInterval("__popup.onresize()",50);
		
		
		$addEL(container,"scroll",$dele(this,"onresize"),true);
		
		//initialize position at once.
		this.onresize();
		
	},
	
	show : function (){
		
		if (!this.config.display) return;
		
		this.moveTargetPosition = 0;
		this.status = PopUp.MOVE_UP;
		this.startMove();
	},
	
	hide : function (){
		
		this.moveTargetPosition = this.config.height;
		this.status = PopUp.MOVE_DOWN;
		this.startMove();
	},
	
	minimize : function (){
		//alert("minimize");
		this.mm = "min";
		this.moveTargetPosition = this.config.height - 20;
		this.status = PopUp.SWITCH_TO_MIN;
		this.startMove();
		
		var s = this.switchButton.style;
		var bg = s.backgroundImage;
		
		if (bg.indexOf(this.imgMin) > -1) {
			bg = bg.replace(this.imgMin,this.imgMax);
			s.backgroundImage = bg;			
		}
	},
	
	maximize : function (){
		//alert("maximize");
		if (!this.config.display) return;
		
		this.mm = "max";
		this.moveTargetPosition = 0;
		this.status = PopUp.SWITCH_TO_MAX;
		this.startMove();
		
		
		var s = this.switchButton.style;
		var bg = s.backgroundImage;
		
		if (bg.indexOf(this.imgMax) > -1) {
			bg = bg.replace(this.imgMax,this.imgMin);
			s.backgroundImage = bg;			
		}
	},
	
	delayHide : function (){		
		window.setTimeout("__popup.hide()",this.config.time.hold);
	},
	
	delayMin : function (){
		window.setTimeout("__popup.minimize()",this.config.time.hold);
	},
	
	switchMode : function (){
		//alert("switch");
		if (this.mm == "min"){
			this.maximize();
		} else {
			this.minimize();
		}
	},
	
	startMove : function (){
		this.stopMove();
		
		this.intervalHandle = window.setInterval("__popup.move()",100);
		
		this.startMoveTime = new Date().getTime();
		//this.startPosition = $dom.getPosition(this.content).y;//parseInt(this.content.style.top);
		this.startPosition = this.config.position.y;
	},
	
	stopMove : function (){
		if (this.intervalHandle != null) window.clearInterval(this.intervalHandle);
		this.intervalHandle = null;
	},
	
	
	move : function (){
		
		
		var t = new Date().getTime();
		t = t - this.startMoveTime;
		
		var total = this.status & PopUp.MOVE_UP ? 
					this.config.time.slideIn : 
					this.config.time.slideOut;
		
		var y = this.config.motionFunc(this.startPosition, this.moveTargetPosition, t/total);
		//this.content.style.top = y + "px";
		this.config.position.y = y;
		this.onresize();
				
		if (t >= total){
			this.onFinishMove();
		}
	},
	
	onFinishMove : function (){
		this.stopMove();
		//this.content.style.top = this.moveTargetPosition + "px";
		
		if (this.status == PopUp.MOVE_UP && this.config.time.hold > 0 ){
			this.delayMin();
		} else {
			if (this.__hackTimer!=null) window.clearInterval(this.__hackTimer);
		}
		this.status = PopUp.STOP;
	},
	
	onresize : function (){
		var c=this.config;
		//var t=document.documentElement;
		var t=document.body;
		
		var dx=t.clientWidth + t.scrollLeft;
		var dy=t.clientHeight + t.scrollTop;
		
		var x = dx - c.right - c.width ;
		var y = dy - c.bottom - c.height + c.position.y;
		
		
		$dom.setPosition(this.popup, x, y);	
		$dom.setSize(this.popup, c.width, c.height-c.position.y);
	}
}

$cpAttr(PopUp.prototype,__o);

/*---------------------------------------*/

function readCookie(name)
{

}

function writeCookie(name, value, hours)
{
  var expire = "";
  if(hours != null)
  {
    expire = new Date((new Date()).getTime() + hours * 3600000);
    expire = "; expires=" + expire.toGMTString();
  }
  document.cookie = name + "=" + escape(value) + expire + ";path=/";
}

/**
* main function to config the pop-up window & run it.
* web deployer change codes here to manipulte popups performance.
* & should not change codes out of this function.
*/
function job(){

	/**
	* config object
	*/
	var cfg={
		//width & height of the popup window ,these values should be determined debpended on inner contents.
		width 			: 256,
		height 			: 159,
		
		//distance to the bottom & the right edge.
		bottom			: 1,
		right			: 1,
		
		//switch of displaying the popup
		display			: true,
			
		//content url
		contentUrl		: "PopupStudent.jsp",
		
		//time configuration,in seconds
		time : {
			slideIn			: 1,
			hold			: 60,
			slideOut		: 1
		}					
	}
	
	//at what time the popup should display,in hours : 0~23,
	//the number after add symbol means after how many the hours to display popup for the next time. 
	var displayTimeList = ["7+7"];
	
	// the popup displays each time thie page reload or only once at the first time page loaded.
	// once / eachTime
	var displayMode = "once";
	//var displayMode = "eachTime";
	
	//cookie name storing the next time to display popup
	var cookieName="sina_blog_popup_next_display_time";
	
	
	
	/**
	* --------------------- from here below, the codes should NOT be modified.
	*/
	var hours={};
	var delays=[];
	for (var i=0;i<displayTimeList.length;i++) {
		var o = displayTimeList[i];
		var ar = o.split("+");
		var t = parseInt(ar[0]);
		for (var m=0;m<ar.length-1;m++){
		    ar[m]=ar[m+1];
		}		
		hours[t]=true;
		for (var j=0;j<ar.length;j++){
			hours[t + parseInt(ar[j])]=true;
		}		
	}
	displayTimeList=[];
	for (var i in hours){
		var s = parseInt(i);
		if (isNaN(s)) continue;
		displayTimeList[displayTimeList.length]=s;
	}
	displayTimeList = displayTimeList.sort();
	//alert(displayTimeList);
	
	
	var pp = new PopUp("xp", cfg);
	window.__popup=pp;
	pp.create();
	
	
	
	//display:
	
	var n=readCookie(cookieName);	
	
	if (displayMode=="eachTime") 
		pp.show();
	else {
		var tm=new Date().getTime();
		if (n==null || tm>n) {
			pp.show();
			
			//get next display time

		}
	}
}

function doit(){
	if (document.body == null) {		
		window.setTimeout(doit,500);
		return;
	}
	
	job();
}

//var dbg=document.getElementById("dbg");

//window.alert=function (m){
	//dbg.value+=m+"\n";
//}

//doit();
function NeoneoStudent() {
//if (document.all){
window.onload = job;
//}
}

// 这个 js 的 入口在这里，先注释掉，在 页面中应用
//NeoneoStudent();