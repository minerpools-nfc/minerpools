<template>
	<view>
		<view  class="" style="padding:0 20rpx;box-sizing: border-box;display:flex;flex-direction: column;justify-content: center;width: 90%;height: 250rpx;background: #FFFFFF;box-shadow: 0px 7px 21px 0px rgba(0, 52, 157, 0.1);border-radius: 11px;margin:20rpx auto">
			<view class="" style="display:flex;justify-content: space-between;font-size: 28rpx;margin:4rpx;margin-left:10rpx">
				<view class="">
					<text style="display:inline-block;width:180rpx">用户名:</text>
					<text style="display:inline-block">{{myInfo.loginname}}</text>
				</view>
				
			</view>
			<view class="" style="display:flex;justify-content: space-between;font-size: 28rpx;margin:4rpx;margin-left:10rpx;margin-top:30rpx">
				<view class="" style="display:flex;align-items: center;">
					<text style="display:inline-block;width:180rpx">密码:</text>
					<input type="password" :value="myInfo.loginpwd" disabled=""/>
				</view>
				
			</view>
			<view class="" style="display:flex;justify-content: flex-end;margin-top:10rpx">
				<view class="" @click="update" style="margin-right:28rpx;font-size:30rpx;color:#757bff">
					修改密码
				</view>
			</view>
		</view>
		<view  class="" style="padding:0 20rpx;box-sizing: border-box;display:flex;flex-direction: column;justify-content: center;width: 90%;height: 370rpx;background: #FFFFFF;box-shadow: 0px 7px 21px 0px rgba(0, 52, 157, 0.1);border-radius: 11px;margin:20rpx auto">
			<view class="" style="display:flex;align-items: center;width:100%;width:90%">
				<view class=""  style="font-size:28rpx;height:60rpx;line-height: 60rpx;">
					钱包地址：
				</view>
				
			</view>
			<view class="" style="display:flex;align-items: center;padding:20rpx 40rpx;position:width:100%;padding-top:0;margin-top:20rpx">
				<view class="" style="display:flex;align-items: center;background:#f5f5f5;flex:1;height:60rpx;border-radius:40rpx;">
					<input type="text" v-model="myValue" style="height:60rpx;line-height: 60rpx;flex:1;margin-left:20rpx;font-size:24rpx" value=""
					 placeholder="请输入钱包地址并提交..." />
					 <image @click="myValue=''" v-show="myValue" src="/static/cha.png" mode="" style="width:26rpx;height:26rpx;margin-right:28rpx"></image>
				</view>
			</view>
			<view class="" style="display:flex;justify-content: flex-end;margin-top:10rpx">
				<view class=""  @click="submit" style="margin-right:28rpx;font-size:30rpx;color:#757bff;margin-top:20rpx">
					提交
				</view>
			</view>
		</view>
		<!-- <view class="" style="display:flex;align-items: center;padding:20rpx 40rpx;width:100%;width:90%">
			<view class=""  style="font-size:26rpx;color:#757bff;;height:60rpx;line-height: 60rpx;">
				钱包地址：
			</view>
			
		</view>
		<view class="" style="display:flex;align-items: center;padding:20rpx 40rpx;position:width:100%;padding-top:0">
			<view class="" style="display:flex;align-items: center;background:#f5f5f5;flex:1;height:60rpx;border-radius:40rpx;">
				<input type="text" v-model="myValue" style="height:60rpx;line-height: 60rpx;flex:1;margin-left:20rpx;font-size:24rpx" value=""
				 placeholder="请输入钱包地址并提交..." />
				 <image @click="myValue=''" v-if="myValue.length>0" src="/static/cha.png" mode="" style="width:26rpx;height:26rpx;margin-right:28rpx"></image>
			</view>
			<view class="" @click="submit" style="font-size:28rpx;color:#757bff;padding-left:30rpx;height:60rpx;line-height: 60rpx;">
				提交
			</view>
		</view> -->
		
		<!-- 遮罩层 -->
		<view class="" catchtouchmove="true" @click="isShow=!isShow" @touchmove.stop.prevent="moveHandle" v-show="isShow"
		 style="background: rgba(0,0,0,0.3);width:100%;height:100%;position:fixed;z-index:999;top:0">
		</view>
		<view v-show="isShow" class="" style="width:92%;position:fixed;background:white;border-radius: 20rpx;height:400rpx;top:30%;left:4%;z-index:9999;display:flex;flex-direction: column;justify-content: center;align-items: center;">
			<view class="" style="display:flex;align-items: center;width:80%;padding-top:0;justify-content: center;">
				<view class="" style="display:flex;align-items: center;background:#f5f5f5;flex:1;height:60rpx;border-radius:40rpx;">
					<view class="" style="font-size:24rpx;width:120rpx;margin-left:20rpx">
						密码:
					</view>
					<input type="text" v-model.trim="myPwd" style="height:60rpx;line-height: 60rpx;flex:1;margin-left:20rpx;font-size:24rpx" value=""
					 placeholder="请输入新密码" />
					 <image @click="myPwd=''" v-if="myPwd" src="/static/cha.png" mode="" style="width:26rpx;height:26rpx;margin-right:28rpx"></image>
				</view>
			</view>
			<view class="" style="display:flex;align-items: center;padding:20rpx 40rpx;width:80%;margin-top:30rpx">
				<view class="" style="display:flex;align-items: center;background:#f5f5f5;flex:1;height:60rpx;border-radius:40rpx;">
					<view class="" style="font-size:24rpx;width:120rpx;margin-left:20rpx">
						确认密码:
					</view>
					<input type="text" v-model.trim="myPwdAgain" style="height:60rpx;line-height: 60rpx;flex:1;margin-left:20rpx;font-size:24rpx" value=""
					 placeholder="确认密码并提交" />
					 <image @click="myPwdAgain=''" v-if="myPwdAgain" src="/static/cha.png" mode="" style="width:26rpx;height:26rpx;margin-right:28rpx"></image>
				</view>
			</view>
			<view v-if="flag==1" style="color:red;font-size:24rpx">
				*两次密码输入不一致
			</view>
			<view v-if="flag==2" style="color:red;font-size:24rpx">
				*密码长度不能低于六位
			</view>
			<button @click="sub" type="default"  style="background:#757bff;color:white;width:50%;margin-top:50rpx;height:60rpx;line-height: 60rpx;font-size:26rpx;border-radius: 40rpx;">提交</button>
		</view>
		
		 <button class="" style="position:fixed;bottom:200rpx;left:10%;font-size:26rpx;color:white;width:80%;background:#817ef4;height:100rpx;line-height:100rpx;border-radius:50rpx" @click="logout">退出</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				host: uni.getStorageSync("myValue")+uni.getStorageSync("name")+"/api",
				myValue:"",
				myInfo:"",
				isShow:false,
				myPwd:"",
				myPwdAgain:"",
				flag:""
			}
		},
		methods: {
			logout(){
				uni.removeStorageSync('mtoken');
				uni.removeStorageSync('user');
				uni.reLaunch({
				    url: '/pages/login/login'
				});
			},
			update(){
				this.isShow=true
				this.flag=""
				this.myPwd=""
				this.myPwdAgain=""
			},
			async submit(){
				// if(this.myValue.length<20){
				// 	return this.sc.showMsg("钱包地址不小于20位！")
				// }
				let result=await this.sc.callApi({
					
					data:{
						wallet_address:this.myValue
					},
					url:"/BindUsers/myinfo",
					host:this.host,
					method:"POST"
				})
				if(result.success){
					console.log(result.data)
					this.sc.showMsg("提交成功！")
				}
			},
			async sub(){
				if(this.myPwd!=this.myPwdAgain){
					return this.flag=1
				}
				if(this.myPwd.length<6){
					return this.flag=2
				}
				let result=await this.sc.callApi({
					data:{
						password:this.myPwd,
						oldpassword:this.myInfo.loginpwd
					},
					url:"/BindUsers/myinfo",
					host:this.host,
					method:"POST"
				})
				if(result.success){
					this.isShow=false
					console.log(result.data)
					this.sc.showMsg("修改成功！")
				}
			},
			moveHandle(){},
			async loadData(){
				let result=await this.sc.callApi({
					url:"/BindUsers/myinfo",
					host:this.host,
				})
				if(result.success){
					console.log(result.data)
					this.myInfo=result.data
					this.myValue=result.data.wallet_address
				}
			},
		},
		onShow(){
			this.loadData()
		}
	}
</script>

<style>

</style>
