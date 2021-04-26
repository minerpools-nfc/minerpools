<template>
	<view class="content" style="height:100%">
		<view
			style="background-image: url(../../static/backIndex.png);background-size:112% 100%;width:100%;background-position:32% 0%"
			:style="{height:550-height*2+'rpx'}">
			<!-- #ifdef APP-PLUS -->
			<view class="" style="display:flex;align-items: center;"
				:style="{paddingTop:height*2+'rpx',paddingLeft:30+'rpx'}">
				<image src="/static/inlogo.png" mode="" style="width:196rpx;height:38rpx;"></image>
				<!-- <image src="/static/NetFlowCoin.png" mode="" style="width:156rpx;height:20rpx;margin-left:10rpx"></image> -->
			</view>
			<!-- #endif -->
			<!-- #ifdef H5 -->
			<view class="" style="display:flex;align-items: center;"
				:style="{paddingTop:height*2+20+'rpx',paddingLeft:30+'rpx'}">
				<image src="/static/inlogo.png" mode="" style="width:196rpx;height:38rpx;"></image>
				<!-- <image src="/static/NetFlowCoin.png" mode="" style="width:156rpx;height:20rpx;margin-left:10rpx"></image> -->
			</view>
			<!-- #endif -->
			<view @click="sc.goTo('/pages/index/income')" class=""
				style="color:white;font-size:32rpx;display:flex;justify-content: center;margin-top:40rpx">
				我的收益
			</view>
			<view @click="sc.goTo('/pages/index/income')" class=""
				style="color:white;font-size:46rpx;display:flex;justify-content: center;margin-top:20rpx">
				{{cltype.myallnfc}}
			</view>
			<view class="" style="display:flex;margin-top:50rpx;font-size:30rpx">
				<view @click="sc.goTo('/pages/index/income')" class=""
					style="width:50%;display:flex;flex-direction: column;justify-content: center;align-items: center;color:white">
					<view class="" style="font-size:28rpx;margin-bottom:10rpx">
						{{cltype.my_locknfc}}
					</view>
					<view class="">
						锁仓
					</view>
				</view>
				<view @click="sc.goTo('/pages/index/income')" class=""
					style="width:50%;display:flex;flex-direction: column;justify-content: center;align-items: center;color:white">
					<view class="" style="font-size:28rpx;margin-bottom:10rpx">
						{{cltype.my_unlocknfc}}
					</view>
					<view class="">
						解锁
					</view>
				</view>
			</view>
		</view>
		<view class=""
			style="width:90%;margin:0 auto;border: 4rpx solid #DAE2FF;border-radius: 56rpx;height: 340rpx;display:flex;align-items: center;padding:0 50rpx;box-sizing: border-box;">
			<view style="display: flex;justify-content: center;position: relative;">
				<cmd-progress type="circle" :percent="cltype.mydevices_online/cltype.mydevice_num*100"
					stroke-color="#5079ff" :stroke-width="8" :width="160" stroke-shape="square" :showInfo="false">
				</cmd-progress>
				<view
					style="position: absolute;text-align: center;display:flex;align-items: center;width:160rpx;height:160rpx;justify-content: center;">
					{{cltype.mydevice_num}}
				</view>
			</view>

			<view class="" style="flex:1" @click="sc.goTo('/pages/device/device',4)">
				<view class="" style="display:flex;justify-content: space-between;align-items: center;">
					<view class=""
						style="width:50%;display:flex;justify-content: center;font-size:34rpx;color:#416eff;font-weight:600;font-family: Microsoft YaHei;">
						矿机数
					</view>
					<image src="/static/you.png" mode="" style="width:30rpx;height:30rpx"></image>
				</view>
				<view class="" style="display:flex;margin-top:30rpx;font-size:34rpx;flex-wrap: wrap;">
					<view class=""
						style="width:50%;display:flex;flex-direction: column;justify-content: center;align-items: center;">
						<view class="" style="font-size:28rpx;margin-bottom:10rpx;font-size:28rpx;color:#416eff">
							在线矿机
						</view>
						<view class="" style="">
							{{cltype.mydevices_online}}
						</view>
					</view>
					<view class=""
						style="width:50%;display:flex;flex-direction: column;justify-content: center;align-items: center;">
						<view class="" style="font-size:28rpx;margin-bottom:10rpx;font-size:28rpx;color:#416eff">
							离线矿机
						</view>
						<view class="" style="">
							{{cltype.mydevices_offline}}
						</view>
					</view>
					
					<!-- <view class=""
						style="width:50%;display:flex;flex-direction: column;justify-content: center;align-items: center;margin-top:30rpx">
						<view class="" style="font-size:28rpx;margin-bottom:10rpx;font-size:28rpx;color:#416eff">
							托管矿机
						</view>
						<view class="" style="">
							{{cltype.mydevices_tuoguan}}
						</view>
					</view>
					<view class=""
						style="width:50%;display:flex;flex-direction: column;justify-content: center;align-items: center;margin-top:30rpx">
						<view class="" style="font-size:28rpx;margin-bottom:10rpx;font-size:28rpx;color:#416eff">
							租用矿机
						</view>
						<view class="" style="">
							{{cltype.mydevices_zuyong}}
						</view>
					</view> -->
				</view>
			</view>
		</view>

		<view class="" style="margin-top:28rpx;padding-bottom:120rpx">
			<view @click="sc.goTo('/pages/draw/waitDraw/waitDraw')"
				style="border-top:4rpx solid #f3f2f2;height:150rpx;display:flex;align-items: center;padding:0 40rpx;justify-content: space-between;">
				<view class="" style="display:flex;align-items: center;">
					<image src="/static/jifen.png" mode="" style="width:110rpx;height:110rpx"></image>
					<view class="" style="margin-left:40rpx;font-size:30rpx">
						<view class="" style="font-weight:bold">
							待提取
						</view>
						<!-- <view class="" style="color: #9CB1FF;font-size:26rpx;margin-top:10rpx">
							2021.1.1
						</view> -->
					</view>
				</view>
				<view class="" style="font-weight:bold;font-size:34rpx">
					{{cltype.my_waitdraw}}
				</view>
			</view>
			<view @click="sc.goTo('/pages/draw/draw',4)" class=""
				style="border-top:4rpx solid #f3f2f2;border-bottom: 4rpx solid #f3f2f2;height:150rpx;padding:0 40rpx;display:flex;align-items: center;justify-content: space-between;">
				<view class="" style="display:flex;align-items: center;">
					<image src="/static/suocang.png" mode="" style="width:110rpx;height:110rpx"></image>
					<view class="" style="margin-left:40rpx;font-size:30rpx">
						<view class="" style="font-weight:bold">
							已提取
						</view>
						<!-- <view class="" style="color: #9CB1FF;font-size:26rpx;margin-top:10rpx">
							2021.1.1
						</view> -->
					</view>
				</view>
				<view class="" style="font-weight:bold;font-size:34rpx">
					{{cltype.my_hasdraw}}
				</view>
			</view>
			<view class="" @click="sc.goTo('/pages/draw/draw',4)"
				style="height:150rpx;padding:0 40rpx;display:flex;align-items: center;justify-content: space-between;">
				<view class="" style="display:flex;align-items: center;">
					<image src="/static/tiqu.png" mode="" style="width:110rpx;height:110rpx"></image>
					<view class="" style="margin-left:40rpx;font-size:30rpx">
						<view class="" style="font-weight:bold">
							提取中
						</view>
						<!-- <view class="" style="color: #9CB1FF;font-size:26rpx;margin-top:10rpx">
							2021.1.1
						</view> -->
					</view>
				</view>
				<view class="" style="font-weight:bold;font-size:34rpx">
					{{cltype.my_indraw}}
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import cmdProgress from "@/components/cmd-progress/cmd-progress.vue"
	var that;
	export default {
		data() {
			return {
				flag: true,
				index: 0,
				index2: 0,
				menuShow: false,
				shebeiStatus: ["所有", "在线", "离线"],
				height: null, //获取的状态栏高度
				pageNum: 1, //查询页面
				pageSize: 5, //页面数量	
				pageListData: [],

				lowmbpoit: "",
				pageLoadingStatus: '',
				fhStatus: [],
				cltype: "",
				current: true,
				host: uni.getStorageSync("myValue") + uni.getStorageSync("name") + "/api",
			}
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
			that = this;
		},
		components: {
			cmdProgress
		},
		onShow() {
			this.fhStatus = []
			this.loadData()
			this.pageNum = 1;
			this.loadPageData();
			this.menuShow = false
		},
		methods: {
			cons() {
				console.log(111)
			},
			change2() {
				console.log(111)
			},
			change() {
				this.current = !this.current;
				this.pageNum = 1
				this.loadPageData()
			},
			bindPickerChange: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.index = e.target.value
				this.pageNum = 1

				this.loadPageData()
			},
			bindPickerChange2: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.pageNum = 1
				this.index2 = e.target.value
				this.loadPageData()
			},
			filterA(item) {
				if (item == 1) {
					return "在线"
				} else {
					return "离线"
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
					url: "/index/appstatic",
					host: this.host
				});
				if (result.success) {
					console.log(result.data)
					this.cltype = result.data

				}

			},
			async loadPageData() {

				this.pageLoadingStatus = 'loading';
				var pdata = {
					pageNum: this.pageNum,
					pageSize: this.pageSize,
					online_status: this.index ? this.index : "",
					lowmbpoit: this.current ? "" : "1",
					typeid: this.index2 ? this.cltype[this.index2 - 1].id : ""
				}

				let result = await this.sc.callApi({
					data: pdata,
					url: "/devices/index",
					method: "POST",
					host: this.host
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

		onReachBottom() {
			if (this.pageLoadingStatus === 'more') {
				this.pageNum = this.pageNum + 1;
				this.loadPageData();
			}
		},
	}
</script>

<style scoped>
	.tab {
		border: 1px solid yellow;
		color: red
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
		justify-content: space-around;
		margin-top: 100rpx;
	}

	.shebeiStatus {
		display: flex;
	}

	.jifen {
		border: 1px solid #C0C0C0;
		border-radius: 10rpx;
		padding: 2rpx 6rpx
	}

	.shebeiContent {
		margin: 0 4%;
		padding: 20rpx;

	}

	.shebeiList {
		display: flex;
		align-items: center;
		margin: 10rpx 0;
		font-size: 30rpx
	}

	.btn {
		width: 160rpx;
		height: 50rpx;
		line-height: 50rpx;
		text-align: center;
		font-size: 24rpx;
		border-radius: 40rpx;
		border: 2rpx solid #08b6f2;
		margin-left: 40rpx;
	}
</style>
