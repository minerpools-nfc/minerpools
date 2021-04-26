<template>
	<view style="height:100%" @click="menuShow=false">
		<view class="status_bar" :style="{height:height+50+'px'}">
			<view class="head">
				<view class="" style="width:40rpx;height:40rpx;display:flex;align-items: center;"  @click="sc.goTo('',5)">
					<image src="/static/zuo.png"  style="width:40rpx;height:40rpx"></image>
				</view>
				<view class="" @click="sc.goTo('/pages/login/login')">
					用户管理
				</view>
				<image src="/static/caidan.png" style="width:50rpx;height:50rpx" @click.stop="menuShow=!menuShow"></image>
				<!-- <view class="menu" v-if="menuShow">
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
				
				<view v-if="menuShow" style="position:fixed;width:100px;right:0;top:50px;border-left:1px solid #eeeeee;z-index:1;color:black">
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
				</view>
			</view>
		</view>
		
		<view class="incomeTable" :style="{marginTop:60+height+'px'}">
			<view class="titleAll">
				<view class="title">
					分组
				</view>
				<!-- border-left:1px solid #eeeeee;border-right:1px solid #eeeeee -->
				<view class="title" style="">
					账号
				</view>
				<view class="title">
					操作
				</view>
			</view>
			<view class="incomeContent" v-for="(data,index) in userList" :key="index">
				<view class="income">
					{{data.auth_group_id}}
				</view>
				<view class="income" >
					{{data.username}}
				</view>
				<view class="income">
					<view class=""  style="display:flex;justify-content: space-around;align-items: center;height:100%">
						<view class="btn" @click="sc.goTo('/pages/users/updateuser?myid='+data.id)">
							修改
						</view>
						<view class="btn" @click="deleteUser(data.id)" >
							删除
						</view>
					</view>
				</view>
			</view>
			
			
		</view>
		
		<view class="buttonBox" @click="sc.goTo('/pages/users/adduser')">
			新增用户
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				menuShow:false,
				userList:[],
				host:uni.getStorageSync("name")+":"+uni.getStorageSync('dk')+"/api"
			}
		},
		methods: {
			async deleteUser(id){
				let result = await this.sc.callApi({
					data: {
						id
					},
					url: "/user/delete",
					method:"POST",
					host:this.host
				});
				if (result.success) {
					this.sc.showMsg("删除成功")
					this.loadData()
				}
			},
			go(url) {
				console.log(11)
				uni.navigateTo({
					url
			
				});
			},
			async loadData() {
				let result = await this.sc.callApi({
					data: {},
					url: "/user/index",
					host:this.host
				});
				if (result.success) {
					this.userList = result.data;
				}
			},
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
		onShow(){
			console.log(111)
			this.loadData()
		}
	}
</script>

<style lang="scss">
.status_bar {
	        width: 100%;
	        background: #08b6f2;
	        position: relative;
			position: fixed;
			top: 0;
	    }
	    /* 调整状态栏标题的位置 */
	
	

	
	.head {
		bottom:0;
		left:0;
		right:0;
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

	.menu{
		position:absolute;
		right:0;
		top:40px;
		border:1px solid #C0C0C0;
		border-radius:4rpx;
		background:white;
		color:#999999
	}
	.menuList{
		margin:0 10rpx;
		height:60rpx;
		line-height:60rpx
	}
	.incomeTable{
		width:96%;
		margin:0 auto;
		margin-top:30rpx;
		
		.titleAll{
			display:flex;
			height:80rpx;
			line-height:80rpx;
			font-size:30rpx;
			font-weight: bold;
			justify-content: space-between;
			border-bottom:1px solid #eeeeee;
			.title{
				width:33%;
				text-align: center;
			}
		}
		.incomeContent{
			display:flex;
			height:120rpx;
			line-height:120rpx;
			font-size:26rpx;
			justify-content: space-between;
			.income{
				width:33%;
				text-align: center;
			}
		}
	}
	.btn {
		width: 100rpx;
		height: 40rpx;
		line-height: 40rpx;
		text-align: center;
		font-size: 24rpx;
		border-radius: 40rpx;
		border: 2rpx solid #08b6f2;
		color:#08b6f2
	}
	.buttonBox{
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
</style>
