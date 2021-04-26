<template>
	<view style="height:100%" @click="menuShow=false">
		<view class="status_bar" :style="{height:height+50+'px'}" style="position:fixed;top:0;z-index:1">
			<view class="head">
				<view class="" style="width:40rpx;height:40rpx;display:flex;align-items: center;" @click="sc.goTo('',5)">
					<image src="/static/zuo.png" style="width:40rpx;height:40rpx"></image>
				</view>
				<view class="" @click="sc.goTo('/pages/login/login')">
					设备组
				</view>
				<image src="/static/caidan.png" style="width:50rpx;height:50rpx" @click.stop="menuShow=!menuShow"></image>
				<!-- <view class="menu" v-if="menuShow" >
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
							<view class="" @click.stop="go('/pages/earlywarn/earlywarn')" style="border-bottom:1px solid #cccccc;height: 40px;line-height: 40px;text-align: center;">
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
		<view class="" style="display:flex;justify-content: flex-end;margin-right:60rpx;"  :style="{marginTop:60+height+'px'}">
			<view class="btn" @click="sc.goTo('/pages/deviceGroup/addGroup/addGroup')">
				新增设备组
			</view>
		</view>
		<view class=""  style="display:flex;width:86%;margin:0 auto;height:100rpx;align-items: center;border-bottom:1px solid #EEEEEE" v-for="(data,cd) in dataList1" :key="cd" :class="(cd+1)==dataList1.length?'mar':''">
			<view class="" style="width:200rpx;font-size:30rpx;font-weight:bold">
				设备组{{cd+1}}
			</view>
			<input type="text" v-model="data.name"  style="flex:1;margin-left:60rpx;font-size:28rpx;" placeholder="请输入设备组名"/>
			<view class="" @click="deleteData1(data.id)" style="margin-left:30rpx;border:1px solid #08b6f2;border-radius:50rpx;font-size:26rpx;color:#08b6f2;width:100rpx;height:50rpx;line-height:50rpx;text-align: center;">
				删除
			</view>
		</view>
		
		<view class="buttonBox" @click="submit">
			保存
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				host:uni.getStorageSync("name")+":"+uni.getStorageSync('dk')+"/api",
				menuShow: false,
				dataList1:[],
				cfg1:"",
			}
		},
		methods: {
			valid(value){
				value =value.replace(/\s+/g,"");
				if (value=='') {
					return false;
				} else {
					return true;
				}
			},
			async submit(){
				this.dataList1.forEach(item=>{
					if(!this.valid(item.name)){
						return this.sc.showMsg("设备组不能为空")
					}
				})
				console.log(this.dataList1)
				let result=await this.sc.callApi({
					contentType:'application/json',
					data:this.dataList1,
					url:"/DevicesType/editmore ",
					host:this.host,
					method:"POST"
				})
				if(result.success){
					this.sc.showMsg("修改成功")
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
					url: "/DevicesType/index ",
					host:this.host
				});
				if (result.success) {
					console.log(result.data)
					this.dataList1=result.data
				}
				
			},
			// first
			addList1() {
				this.dataList1.push("")
			},
			async deleteData1(num) {
				// this.dataList1.splice(num,1)
				let result = await this.sc.callApi({
					data:{
						id:num
					},
					url: "/DevicesType/delete",
					host:this.host
				});
				if (result.success) {
					// console.log(result.data)
					// this.dataList1=result.data
					this.sc.showMsg("删除成功")
				}
			},
			change1(e){
				this.cfg1.enable=e.detail.value
			},
			bindPickerChange1: function(e) {
				this.index = e.target.value
			},
			changeEmail1(e,cd){
				this.dataList1[cd]=e.detail.value
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
			this.loadData()
		}
	}
</script>

<style>
.status_bar {
		width: 100%;
		background: #08b6f2;
		position: relative;
		position: fixed;
		top: 0;
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
		height: 60rpx;
		line-height: 60rpx
	}

	.border_bottom {
		border-bottom: 1px solid #C0C0C0;
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
		color: #08b6f2;
	}
</style>
