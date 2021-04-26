<template>
	<view style="height:100%" @click="menuShow=false">
		<view class="status_bar" :style="{height:height+50+'px'}" style="z-index:1">
			<view class="head">
				<view class="" style="width:40rpx;height:40rpx;display:flex;align-items: center;" @click="sc.goTo('',5)">
					<image src="/static/zuo.png" style="width:40rpx;height:40rpx"></image>
				</view>
				<view class="" @click="sc.goTo('/pages/login/login')">
					邮箱配置
				</view>
				<image src="/static/caidan.png" style="width:50rpx;height:50rpx" @click.stop="menuShow=!menuShow"></image>
				<!-- <view class="menu" v-if="menuShow" :style="menuShow?{zIndex:1}:{zIndex:-1}">
					<view class="menuList border_bottom" @click.stop="go('/pages/index/index')">
						设备列表
					</view>
					<view class="menuList border_bottom" @click.stop="go('/pages/config/config')">
						接口配置
					</view>
					<view class="menuList border_bottom" @click.stop="go('/pages/users/users')">
						用户管理
					</view>
					<view class="menuList border_bottom" @click.stop="go('/pages/earlywarn/allocation')">
						预警配置
					</view>
					<view class="menuList border_bottom" @click.stop="go('/pages/earlywarn/earlywarn')">
						预警记录
					</view>
					<view class="menuList border_bottom" @click.stop="go('/pages/bonus/bonus')">
						分红配置
					</view>
					<view class="menuList border_bottom" @click.stop="go('/pages/bonusrecord/bonusrecord')">
						分红记录
					</view>
					<view class="menuList" @click.stop="go('/pages/emailsetting/emailsetting')">
						邮箱配置
					</view>
				</view> -->
				
				<cover-view v-if="menuShow" style="position:fixed;width:100px;right:0;top:50px;border-left:1px solid #eeeeee;z-index:1;color:black" :style="menuShow?{zIndex:1}:{zIndex:-1}">
					<uni-collapse ref="add" class="warp" @change="" style="width:100px;border-left:1px solid #eeeeee;">
						<uni-collapse-item    :show-animation="true"
						  title="设备">
							<view class="" @click.stop="go('/pages/index/index')" style="border-bottom:1px solid #cccccc;height: 40px;line-height: 40px;text-align: center;">
								设备列表
							</view>
							<view class="" @click.stop="go('/pages/deviceGroup/deviceGroup')" style="height: 40px;line-height: 40px;text-align: center;">
								设备组
							</view>
						</uni-collapse-item>
					</uni-collapse>
					
					<!-- 传show属性是否显示右边箭头 -->
					<uni-collapse ref="add"  class="warp" @change="go('/pages/bonusrecord/bonusrecord')" style="width:100px;border-left:1px solid #eeeeee">
						<uni-collapse-item    :show-animation="true" 
						  title="分红">
						</uni-collapse-item>
					</uni-collapse>
					<uni-collapse ref="add"  class="warp" @change="go('/pages/earlywarn/earlywarn')" style="width:100px;border-left:1px solid #eeeeee">
						<uni-collapse-item    :show-animation="true" 
						  title="预警">
						</uni-collapse-item>
					</uni-collapse>
					<uni-collapse ref="add" class="warp" @change="" style="width:100px;border-left:1px solid #eeeeee">
						<uni-collapse-item    :show-animation="true"
						  title="设置">
							<view class="" @click.stop="go('/pages/users/users')" style="border-bottom:1px solid #cccccc;height: 40px;line-height: 40px;text-align: center;">
								用户管理
							</view>
							<view class="" @click.stop="go('/pages/config/config')" style="border-bottom:1px solid #cccccc;height: 40px;line-height: 40px;text-align: center;">
								接口配置
							</view>
							<view class="" @click.stop="go('/pages/earlywarn/allocation')" style="border-bottom:1px solid #cccccc;height: 40px;line-height: 40px;text-align: center;">
								告警配置
							</view>
							<view class="" @click.stop="go('/pages/emailsetting/emailsetting')" style="height: 40px;line-height: 40px;text-align: center;">
								邮件发送
							</view>
						</uni-collapse-item>
					</uni-collapse>
				</cover-view>
			</view>
		</view>

		<view class="jifen">
			
			<view class="line">
				<view class="myTitle">
					发件人
				</view>
				<input class="myInput" type="text" v-model="dataObj.email_name" placeholder="请输入显示名称" />
			</view>
			<view class="line">
				<view class="myTitle">
					发件服务器
				</view>
				<input class="myInput" type="text" v-model="dataObj.hostname" placeholder="请输入发件服务器" />
			</view>
			<view class="line">
				<view class="myTitle">
					ssl
				</view>
				<!-- <input class="myInput" type="text" v-model="dataObj.ssl_enable" placeholder="请输入ssl" /> -->
				<radio-group @change="change" :value="status" style="vertical-align: middle;font-size:28rpx;font-weight:bold;">
					<label class="radio" style="margin-left: 20upx;">
						<radio style="transform:scale(0.7)" value="1" :checked="dataObj.ssl_enable==1?true:false" />启用
					</label>
					<label class="radio" style="margin-left: 20upx;">
						<radio style="transform:scale(0.7)" value="0" :checked="dataObj.ssl_enable==0?true:false"/>不启用
					</label>
				</radio-group>
			</view>
			<view class="line">
				<view class="myTitle">
					端口
				</view>
				<input class="myInput" type="number" v-model="dataObj.eamilport" placeholder="请输入端口号" />
			</view>
			<view class="line">
				<view class="myTitle">
					邮箱地址
				</view>
				<input class="myInput" type="text" v-model="dataObj.emailaddr" placeholder="请输入邮箱地址" />
			</view>
			<view class="line">
				<view class="myTitle">
					邮箱用户
				</view>
				<input class="myInput" type="text" v-model="dataObj.authname" placeholder="请输入邮箱用户" />
			</view>
			<view class="line">
				<view class="myTitle">
					邮箱密码
				</view>
				<input class="myInput" type="password" v-model="dataObj.authpwd" placeholder="请输入邮箱密码" />
			</view>


			<!-- <view class="" style="display:flex;justify-content: flex-end;margin-right:60rpx;margin-top:40rpx">
				<view class="btn" @click="addList">
					新增邮箱用户
				</view>
			</view> -->
		</view>
		<!-- <view class="" style="margin-bottom:100px">
			<view style="margin-bottom:20px" v-for="(data,cd) in dataList" :key="cd" :class="(cd+1)==dataList.length?'mar':''">
				<view class="emailTitle" style="margin-left:4%;font-size:30rpx;font-weight:bold">
					邮箱用户{{cd+1}}
				</view>
				<view class="" style="display:flex;align-items: center;margin-left:12%;">
					<view class="">
						<view class="" style="margin-top:20rpx;font-size:28rpx;display:flex">
							<view class="">
								邮箱用户:
							</view>
							<input @input="myInput($event,cd)"  type="text" v-model="data.name" style="border-bottom:1px solid #EEEEEE;margin-left:20rpx" />
						</view>
						<view class="" style="margin-top:20rpx;font-size:28rpx;display:flex">
							<view class="">
								邮箱密码:
							</view>
							<input  type="text" value="" style="border-bottom:1px solid #EEEEEE;margin-left:20rpx" />
						</view>
					</view>
					<view class="" @click="deleteData(cd)" style="margin-left:30rpx;border:1px solid #08b6f2;border-radius:50rpx;font-size:26rpx;color:#08b6f2;width:100rpx;height:50rpx;line-height:50rpx;text-align: center;">
						删除
					</view>
				</view>
			</view>

		</view> -->
		<view class="buttonBox" @click="submit">
			保存
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				status:"1",
				host:uni.getStorageSync("name")+":"+uni.getStorageSync('dk')+"/api",
				data: "test",
				dataList: [{
					name:123,
				}],
				menuShow: false,
				height: null, //获取的状态栏高度
				dataObj: {
					authname: "",
					authpwd: "",
					eamilport: "",
					email_name: "",
					emailaddr: "",
					hostname: "",
					ssl_enable: "",
				},
				oldauthpwd:""
			}
		},
		methods: {
			change(){
				this.dataObj.ssl_enable=e.detail.value
			},
			valid(value){
				value =value.replace(/\s+/g,"");
				if (value=='') {
					return false;
				} else {
					return true;
				}
			},
			async submit(){
				
				if(!this.valid(this.dataObj.email_name)){
					return this.sc.showMsg("发件人不能为空")
				}
				if(!this.valid(this.dataObj.hostname)){
					return this.sc.showMsg("发件服务器不能为空")
				}
				// if(!this.valid(this.dataObj.ssl_enable)){
				// 	return this.sc.showMsg("ssl不能为空")
				// }
				if(!(this.valid(this.dataObj.eamilport+""))){
					return this.sc.showMsg("端口不能为空")
				}
				if(!this.valid(this.dataObj.emailaddr)){
					return this.sc.showMsg("邮箱地址不能为空")
				}
				if(!this.valid(this.dataObj.authname)){
					return this.sc.showMsg("邮箱用户不能为空")
				}
				if(!this.valid(this.dataObj.authpwd)){
					return this.sc.showMsg("邮箱密码不能为空")
				}
				
				let result = await this.sc.callApi({
					data:{
						authname: this.dataObj.authname,
						authpwd: this.dataObj.authpwd,
						eamilport: this.dataObj.eamilport,
						email_name: this.dataObj.email_name,
						emailaddr: this.dataObj.emailaddr,
						hostname: this.dataObj.hostname,
						ssl_enable: this.dataObj.ssl_enable,
						oldauthpwd:this.oldauthpwd
					},
					url: "/setting/email",
					method:"POST",
					host:this.host
				});
				if (result.success) {
					this.loadData()
					this.sc.showMsg("保存成功")
				}
			},
			myInput(e,cd){
				this.dataList[cd].name=e.detail.value
			},
			addList() {
				this.dataList.push({
					name: ""
				})
				console.log(this.dataList)
			},
			deleteData(num) {
				console.log(this.dataList)
				this.dataList.splice(num,1)
			},
			go(url) {
				uni.navigateTo({
					url
			
				});
			},
			async loadData() {
				let result = await this.sc.callApi({
					url: "/setting/email",
					host:this.host
				});
				if (result.success) {
					console.log(result.data)
					this.dataObj=result.data
					this.oldauthpwd=result.data.authpwd
				}
				
			},

		},
		onShow(){
			this.loadData()
		},
		onLoad() {
			var _this = this;
			// 获取手机状态栏高度
			uni.getSystemInfo({
				success: function(data) {
					// 将其赋值给this
					_this.height = data.statusBarHeight;
				}
			})
		},
	}
</script>

<style scoped>
	.mar {
		padding-bottom: 100px
	}

	.buttonBox {
		width: 91%;
		margin: 0 auto;
		height: 84rpx;
		border-radius: 84rpx;
		color: white;
		display: flex;
		align-items: center;
		justify-content: center;
		position: fixed;
		bottom: 100rpx;
		left: 0;
		right: 0;
		background-color: #08b6f2;
		z-index: 999
	}

	.btn {
		width: 180rpx;
		height: 60rpx;
		line-height: 60rpx;
		text-align: center;
		font-size: 24rpx;
		border-radius: 40rpx;
		border: 2rpx solid #08b6f2;
		margin-left: 40rpx;
		color: #08b6f2
	}

	.status_bar {
		width: 100%;
		background: #08b6f2;
		position: relative;
	}

	/* 调整状态栏标题的位置 */


	page {
		height: 100%
	}

	.head {
		bottom: 0;
		left: 0;
		right: 0;
		position: absolute;
		display: flex;
		justify-content: space-between;
		padding: 0 2%;
		line-height: 40px;
		align-items: center;
		/* position: relative; */
		background: #08b6f2;
		color: white
	}

	.menu {
		position: absolute;
		right: 0;
		top: 40px;
		border: 1px solid #C0C0C0;
		border-radius: 4rpx;
		background: white;
		color: #999999
	}

	.menuList {
		margin: 0 10rpx;
		padding:0 10rpx;
		height: 60rpx;
		line-height: 60rpx;
	}

	.border_bottom {
		border-bottom: 1px solid #eeeeee;
	}

	.line {
		display: flex;
		width: 86%;
		margin: 0 auto;
		height: 70rpx;
		align-items: center;
		border-bottom: 1px solid #EEEEEE
	}

	.myInput {
		width: 100%;
		margin-left: 110rpx;
		font-size: 26rpx;
		font-weight: 500;
		height: 70rpx;
		line-height: 70rpx;
		border-bottom: 1px solid #EEEEEE
	}

	.myTitle {
		width: 250rpx;
		font-size: 30rpx;
		font-weight: bold
	}
</style>
