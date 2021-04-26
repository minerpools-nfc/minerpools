import config from '@/config.js';
export default {
			   name:    config.name,
			   version: config.version,		
			   // apiUrl:  config.apiUrl,
			   apiUrl: uni.getStorageSync("myValue")+uni.getStorageSync("name")+"/api",
			   testToken:config.testToken,
			   imgPrefix:config.imgPrefix,
			   pagechecklogin:function(){
						if (!this.getStoreKey("mtoken")){//判断是否登录						
							this.gologin();
						}else{
							console.log(this.getStoreKey("mtoken"));
							return true;
						}
			  },				  
			  checkUserType:function(){				  
				  if(this.getStoreKey('usertype')!=2&&this.getStoreKey('usertype')!=3){
					  return false;					  
				  }else{
					  return true;
				  }				
			  },
			 setTitle:function(title){			 				
					if(title){  //自定义title时候
						uni.setNavigationBarTitle({
							title: title
						})
					}
			 },			 
			 
			 gologin:function(){				 
				   uni.navigateTo({
					  url: "/pages/user/login",
				   })
			 },
			 setStoreKey:function(key,val){ 				
				try {
			        uni.setStorageSync(key,val);
				} catch (e) {
				   console.log(e)
				}
				
			 },		
			 getStoreKey:function(key){  
				 try {					
					var value = uni.getStorageSync(key);
					if (value) {
						return value;
					}else{
						return "";
					}
				} catch (e) {
					console.log(e)
					return "";
				}
				 
			},
			removeStoreKey:function(key){
				try {		
					uni.removeStorageSync(key);					
				} catch (e) {
					console.log(e)
				}
			},			
			clearStoreKeys:function(){
				try {				
					uni.clearStorageSync();
				} catch (e) {
				   console.log(e)
				}
			},	
				
			showMsg:function(msg,duration = 2000,icon = 'none',mask = false){
					return uni.showToast({
						 title: msg,
						 icon:  icon,
						 duration: duration,
						 mask:mask,
					}); 
			},			
			goTo:function(url,type=1,delta=1){
				//console.log("type: " + type);
				if(type==1){
					return	uni.navigateTo({
						url:url
					})
				}
				if(type==2){
					return uni.redirectTo({
						url:url
					})
				}
				if(type==3){
					return uni.reLaunch({
						url:url
					})
				}
				if(type==4){
					return uni.switchTab({
						url:url
					})
				}
				if(type==5){
					return uni.navigateBack({
						delta:delta
					})
				}
			},
			getImage: function(url) {
				return this.imgPrefix+url;
			},									 
			callApi:function(params,resolve) {
				 return new Promise(resolve => {
					if(!params.showLoading)  uni.showNavigationBarLoading();
					let resultData = {};
					let url =params.host? params.host + params.url : this.apiUrl + params.url;					
					let tk = this.getStoreKey("mtoken");
					let headers={"content-type":"application/x-www-form-urlencoded",'token':tk ,'device':'app'};
					if(params.contentType) {
						headers={"content-type":params.contentType,'token':tk};
					} 
					const requestTask = uni.request({
						url: url,
						timeout: 10000, //超时时间设置为10秒；
						method:  params.method? params.method:"GET",
						data:  params.data,
						header:headers,
						success: (result) => {
							
							if(!params.showLoading) uni.hideNavigationBarLoading();							
							if (result.statusCode == 200) {
								if (result.data.code == 1) {
									resultData.success = true;
									resultData.code = result.data.code;
									resultData.data = result.data.data;
									resultData.msg = result.data.msg;
									resolve(resultData);
								}else if(result.data.code == 9) {
										 setTimeout(function(){
											uni.showToast({
												 title:"登录已失效,请重新登录!",
												 icon: "none",
												 duration: 2000
											});
										 },1000)			
										 uni.navigateTo({
											url: "/pages/user/login",
										 })
										// resultData.success = true;
										// resultData.data =result.data;
										// resolve(resultData); 						 	  
								}else {
									
									resultData.success = false;
									resultData.code = result.data.code;
									resultData.data = result.data.data;
									resultData.msg = result.data.msg ;									
									uni.showToast({
										 title: result.data.msg  ? result.data.msg  : "异常，稍后重试",
										 icon: "none",
										 duration: 2000
									});
									
									
									resolve(resultData);								
								}
							} else {
								
								
								resultData.success= false;
								resultData.msg = result.statusCode;								
								uni.showToast({
									 title:  result.data.msg ?  result.data.msg : "异常，稍后重试("+result.statusCode+")",
									 icon: "none",
									 duration: 2000
								});
								resolve(resultData);
							}
					
						},
						fail: (result) => {
					
							console.log(result);
							if(!params.showLoading) uni.hideNavigationBarLoading();
							resultData.success = false;
							resultData.msg = JSON.stringify(result);
							
							uni.showToast({
								 title: "网络异常",
								 icon: "none",
								 duration: 2000
							});							
							resolve(resultData);
						}
					});
				 })						 								
			}, 					
			goWebview:function(options){
				uni.redirectTo({
					url:"/pages/common/webview?"+options
				})
			}
}