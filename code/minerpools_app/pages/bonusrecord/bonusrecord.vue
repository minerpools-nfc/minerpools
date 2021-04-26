<template>
	<view style="height:100%" @click="menuShow=false">
		<view class="status_bar" :style="{height:height+50+'px'}">
			<view class="head">
				<view class="" style="width:40rpx;height:40rpx;display:flex;align-items: center;" @click="sc.goTo('',5)">
					<image src="/static/zuo.png" style="width:40rpx;height:40rpx"></image>
				</view>
				<view class="" @click="sc.goTo('/pages/login/login')">
					分红管理
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
		<view class="" style="display:flex;justify-content: flex-end;margin-right:60rpx;" :style="{marginTop:60+height+'px'}">
			<view class="btn" @click="sc.goTo('/pages/bonus/bonus')">
				新增
			</view>
		</view>
		<!-- <view class="search">
			<view class="shebeiStatus" style="padding-left:20rpx;box-sizing: border-box;">
				<view style="font-size:30rpx;">日期:</view>
				<view class="jifen">
					<picker mode="date" fields="day" :value="date" @change="bindDateChange" style="margin-left:20rpx;font-size:28rpx;font-weight:bold;display:flex;height:70rpx;align-items: center">
						<view class="uni-input">{{date?date:"请选择"}}<text class="iconfont icon-arrow-down" style="font-size:14px;"></text></view>
					</picker>
				</view>
			</view>

			<view class="shebeiStatus" style="padding-left:20rpx;box-sizing: border-box;">
				<view style="font-size:30rpx;">标签:</view>
				<view class="" style="font-size:28rpx;font-weight:bold;margin-left:20rpx;">
					{{shebeiStatus[0]}}
					<text class='iconfont icon-arrow-down' style="font-size:30rpx;color:#000000;"></text>
				</view>
			</view>

			<view class="shebeiStatus" style="padding-left:20rpx;box-sizing: border-box;">
				<view style="font-size:30rpx;">发放状态:</view>
				<view class="" style="font-size:28rpx;font-weight:bold;margin-left:20rpx;">
					{{shebeiStatus[0]}}
					<text class='iconfont icon-arrow-down' style="font-size:30rpx;color:#000000"></text>
				</view>
			</view>

		</view> -->
		<view class="">
			<view v-for="(item,index) in pageListData" :key="index" style="width:94%;margin:0 auto;padding-bottom:20rpx;border-bottom:1px solid #EEEEEE">
				<view class="content">
					<view class="myItem myTitle columnFirst">
						分红名称
					</view>
					<view class="myItem columnSecond">
						{{item.draw_name}}
					</view>

					<view class="myItem myTitle columnFirst">
						分红类型
					</view>
					<view class="myItem columnSecond">
						{{getdrawidType(item.drawid_type)}}
					</view>
					<view class="myItem myTitle columnFirst" v-if="item.drawid_type==2">
						开始时间
					</view>
					<view class="myItem columnSecond" v-if="item.drawid_type==2">
						{{item.startime}}
					</view>
					<view class="myItem myTitle columnFirst" v-if="item.drawid_type==2">
						结束时间
					</view>
					<view class="myItem columnSecond" v-if="item.drawid_type==2">
						{{item.endtime}}
					</view>
					<view class="myItem myTitle columnFirst">
						分红总额
					</view>
					<view class="myItem columnSecond">
						{{item.total_num}}
					</view>
					<view class="myItem myTitle columnFirst">
						设备总数
					</view>
					<view class="myItem columnSecond">
						{{item.device_num}}
					</view>
					<view class="myItem myTitle columnFirst">
						分红状态
					</view>
					<view class="myItem columnSecond">
						{{getdrawStatus(item.draw_status)}}
						
					</view>
				</view>

				<view class="" style="display:flex;justify-content: flex-end;">
					<view class="btn" @click="deleteA(item.drawid)">
						删除
					</view>
					<view class="btn" style="margin-right:20rpx" @click="sc.goTo('/pages/bonusrecord/recordDetail/recordDetail?drawid='+item.drawid)">
						去执行
					</view>
					
				</view>
			</view>

			<!-- <view v-for="(item,index) in pageListData" :key="index" style="width:94%;margin:0 auto;padding-bottom:20rpx;border-bottom:1px solid #EEEEEE">
				<view class="content">
					<view class="myItem myTitle columnFirst">
						日期
					</view>
					<view class="myItem columnSecond">
						{{item.ctime}}
					</view>
			
					<view class="myItem myTitle columnSecond">
						产生收益
					</view>
					<view class="myItem columnFirst">
						{{item.nfc_num}}
					</view>
					<view class="myItem myTitle columnFirst">
						设备数量
					</view>
					<view class="myItem columnSecond">
						{{item.devicenum}}
					</view>
					<view class="myItem myTitle columnSecond">
						发放失败设备数
					</view>
					<view class="myItem columnFirst">
						{{item.fail_num}}
					</view>
					<view class="myItem myTitle columnFirst">
						发放状态
					</view>
					<view class="myItem columnSecond">
						{{item.draw_status}}
					</view>
					<view class="myItem myTitle columnSecond">
						分红数量
					</view>
					<view class="myItem columnFirst">
						240
					</view>
					<view class="myItem myTitle columnFirst">
						标签
					</view>
					<view class="myItem columnSecond">
						早期分红设备
					</view>
				</view>
			
				<view class="" style="display:flex;justify-content: flex-end;">
					<view class="btn" style="margin-right:20rpx" @click="sc.goTo('/pages/bonusrecord/recordDetail/recordDetail')">
						详情
					</view>
					<view class="btn" @click="sc.goTo('/pages/index/indexDetail')">
						打币
					</view>
				</view>
			</view> -->
		</view>

		<!-- <view class="buttonBox" @click="sc.goTo('/pages/bonus/bonus')">
			设置分红配置
		</view> -->
		
		<uni-load-more :status="pageLoadingStatus" class="xt">
			<view slot="nodatatmp" class="nodata" style="">
				<text>暂无数据</text>
			</view>
		</uni-load-more>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				data: "test",
				menuShow: false,
				shebeiStatus: ["所有", "在线", "离线"],
				date: "",
				host: uni.getStorageSync("name") + ":" + uni.getStorageSync('dk') + "/api",
				pageNum: 1, //查询页面
				pageSize: 10, //页面数量	
				pageListData: [],
				pageLoadingStatus: '',
			}
		},
		methods: {
			async deleteA(drawid) {
				let result = await this.sc.callApi({
					url: "/DeviceDrawInfo/delete",
					data: {
						drawid
					},
					host: this.host,
					method: "POST"
				})
				if (result.success) {
					this.sc.showMsg("删除成功")
					this.pageNum = 1
					this.loadPageData()
				}
			},
			getdrawStatus(item){
				if (item == 1) {
					return "待转账"
				} else if(item == 2){
					return "转出交易中"
				} else if(item == 3){
					return "转账成功"
				} else if(item == 4){
					return "转账失败"
				}
			},
			getdrawidType(item){
				if (item == 1) {
					return "平均分配"
				} else{
					return "按流量贡献比列"
				}
			},
			bindDateChange: function(e) {
				this.date = e.target.value
			},
			go(url) {
				console.log(11)
				uni.navigateTo({
					url

				});
			},
			// async loadData() {
			// 	let result = await this.sc.callApi({
			// 		url: "/DeviceDrawDay/index",
			// 		host:this.host,
			// 		method:"POST"
			// 	});
			// 	if (result.success) {
			// 		console.log(result.data)

			// 	}

			// },
			async loadPageData() {
				this.pageLoadingStatus = 'loading';

				let result = await this.sc.callApi({
					data:{
						pageNum:this.pageNum,
						pageSize:this.pageSize
					},
					url: "/DeviceDrawInfo/index",
					host: this.host,
				})

				let pageList = [];
				if (result.success) {
					pageList = result.data.table.rows;
					if (pageList) {
						// console.log(pageList)
					} else {
						pageList = [];
					}
				}
				if (this.pageNum == 1 && pageList.length == 0) {
					this.pageListData = [];
					this.pageLoadingStatus = 'nodata';
					return;
				}
				this.pageListData = this.pageNum == 1 ? pageList : this.pageListData.concat(pageList);
				this.pageLoadingStatus = pageList.length < this.pageSize ? 'nomore' : 'more';
			},
		},
		onShow() {
			
			this.loadPageData()
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
		onReachBottom() {
			if (this.pageLoadingStatus === 'more') {
				this.pageNum = this.pageNum + 1;
				this.loadPageData();
			}
		},
	}
</script>

<style>
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

	.status_bar {
		width: 100%;
		background: #08b6f2;
		position: relative;
		position:fixed;
		top:0
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

	.search {
		height: 40px;
		display: flex;
		align-items: center;

		margin-top: 20rpx;
		flex-wrap: wrap;
	}

	.shebeiStatus {
		display: flex;
		align-items: center;
		width: 50%;

	}

	.jifen {
		border-radius: 10rpx;
	}

	.content {
		display: flex;
		flex-wrap: wrap;
		width: 100%;
		margin: 0 auto;
		margin-top: 30rpx
	}

	.myItem {
		width: 25%;
		font-size: 26rpx;
		margin-bottom: 6rpx
	}

	.myTitle {
		font-weight: bold;
	}

	.columnFirst {
		width: 20%
	}

	.columnSecond {
		width: 30%
	}

	.btn {
		width: 120rpx;
		height: 40rpx;
		line-height: 40rpx;
		text-align: center;
		font-size: 24rpx;
		border-radius: 40rpx;
		border: 2rpx solid #08b6f2;
		margin-left: 40rpx;
		color: #08b6f2;
		margin-right: 4%
	}
</style>
