function Cookie(key,value)
	{
	    this.key=key;
	    if(value!=null)
	    {
	        this.value=escape(value);
	    }
	    this.expiresTime=null;
	    this.domain=null;
	    this.path="/";
	    this.secure=null;
	}
	Cookie.prototype.setValue=function(value){this.value=escape(value);}
	Cookie.prototype.getValue=function(){return (this.value);}
	
	Cookie.prototype.setExpiresTime=function(time){this.expiresTime=time;}
	Cookie.prototype.getExpiresTime=function(){return this.expiresTime;}
	
	Cookie.prototype.setDomain=function(domain){this.domain=domain;}
	Cookie.prototype.getDomain=function(){return this.domain;}
	
	Cookie.prototype.setPath=function(path){this.path=path;}
	Cookie.prototype.getPath=function(){return this.path;}
	
	Cookie.prototype.Write=function(v)
	{
	    if(v!=null)
	    {
	        this.setValue(v);
	    }
	    var ck=this.key+"="+this.value;
	    if(this.expiresTime!=null)
	    {
	        try
	        {
	            ck+=";expires="+this.expiresTime.toUTCString();;
	        }
	        catch(err)
	        {
	            alert("expiresTime参数错误");
	        }
	    }
	    if(this.domain!=null)
	    {
	        ck+=";domain="+this.domain;
	    }
	    if(this.path!=null)
	    {
	        ck+=";path="+this.path;
	    }
	    if(this.secure!=null)
	    {
	        ck+=";secure";
	    }
	    document.cookie=ck;
	}
	Cookie.prototype.Read=function()
	{
	    try
	    {
	        var cks=document.cookie.split("; ");
	        var i=0;
	        for(i=0;i <cks.length;i++)
	        {
	            var ck=cks[i];
	            var fields=ck.split("=");
	            if(fields[0]==this.key)
	            {
	                this.value=fields[1];
	                return (this.value);
	            }
	        }
	        return null;
	    }
	    catch(err)
	    {
	        alert("cookie读取错误");
	        return null;
	    }
	};