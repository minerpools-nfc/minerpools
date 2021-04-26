<template>
	<view class="content" style="height:100%">
		<!-- <view class="mar" style="position:fixed;width:100%;box-sizing: border-box;z-index:99;background:white;border-bottom:1px solid #f5f5f5">
			<view class="" style="display:flex;height:100rpx;justify-content: space-around;align-items: center;">
				<view class="" @click="getKuangji('')" style="padding:20rpx 20rpx;" :class="cur==''?'current':''">
					托管矿机
				</view>
				<view class="" @click="getKuangji(3)" style="padding:20rpx 20rpx;" :class="cur==3?'current':''">
					租用矿机
				</view>
			</view>
			<view class="" style="display:flex;background:white;align-items: center;padding:20rpx 40rpx;width:100%;box-sizing: border-box;">
				<view class="" style="background:#f5f5f5;display:flex;align-items: center;flex:1;height:60rpx;border-radius:40rpx;">
					<image src="/static/search.png" mode="" style="width:26rpx;height:26rpx;margin-left:28rpx"></image>
					<input type="text" v-model.trim="myValue" style="height:60rpx;line-height: 60rpx;flex:1;margin-left:20rpx;font-size:26rpx" value=""
					 placeholder="请输入SN..." />
					 <image @click="myValue=''" v-if="myValue.length>0" src="/static/cha.png" mode="" style="width:26rpx;height:26rpx;margin-right:28rpx"></image>
				</view>
				<view class="" @click="search" style="font-size:28rpx;color:#757bff;padding-left:30rpx;height:60rpx;line-height: 60rpx;">
					搜索
				</view>
			</view>
		</view> -->
		<view class="mar"
			style="display:flex;background:#f5f5f5;align-items: center;padding:20rpx 40rpx;width:100%;box-sizing: border-box;position:fixed">

			<view class=""
				style="background:white;display:flex;align-items: center;flex:1;height:60rpx;border-radius:40rpx;">
				<image src="/static/search.png" mode="" style="width:26rpx;height:26rpx;margin-left:28rpx"></image>
				<input type="text" v-model.trim="myValue"
					style="height:60rpx;line-height: 60rpx;flex:1;margin-left:20rpx;font-size:26rpx" value=""
					placeholder="请输入SN..." />
				<image @click="myValue=''" v-if="myValue.length>0" src="/static/cha.png" mode=""
					style="width:26rpx;height:26rpx;margin-right:28rpx"></image>
			</view>
			<view class="" @click="search"
				style="font-size:28rpx;color:#757bff;padding-left:30rpx;height:60rpx;line-height: 60rpx;">
				搜索
			</view>
		</view>
		<view class="" style="margin-top:110rpx;padding-bottom:100rpx">
			<!-- 租用不要状态 -->
			<!-- :class="item.device_class==3?'tall':'short'" -->
			<view @click="sc.goTo('/pages/device/deviceIncome/deviceIncome?devicesn='+item.devicesn)"
				v-for="(item,index) in pageListData" :key="index"
				style="padding:10rpx 20rpx;box-sizing: border-box;display:flex;flex-direction: column;justify-content: space-around;width: 90%;background: #FFFFFF;box-shadow: 0px 7px 21px 0px rgba(0, 52, 157, 0.1);border-radius: 11px;margin:20rpx auto;height:100rpx">
				<view class="" style="display:flex;">
					<view class="" style="font-size: 28rpx;width:70%">
						<text style="width:150rpx;display:inline-block">矿机SN:</text>
						<text>{{item.devicesn}}</text>
					</view>
					<view class="" style="font-size: 24rpx;display:flex;flex:1;justify-content: center;">
						<!-- <text style="display:inline-block;margin-right:10rpx">状态:</text> -->
						<text style="color:#49b600;font-weight:bold" v-if="item.online_status==1">运行中</text>
						<text style="color:red;font-weight:bold" v-else-if="item.online_status==0">停止</text>
					</view>
				</view>
				<!-- <view class="" style="display:flex;" v-if="item.device_class==3">
						<view class="" style="font-size: 28rpx;width:70%">
							<text style="width:150rpx;display:inline-block">开始时间:</text>
							<text>{{item.starttime}}</text>
						</view>
						<view class="" style="font-size: 24rpx;display:flex;flex:1;justify-content: center;">
							
						</view>
					</view>
					<view class="" style="display:flex;" v-if="item.device_class==3">
						<view class="" style="font-size: 28rpx;width:70%">
							<text style="width:150rpx;display:inline-block">结束时间:</text>
							<text>{{item.endtime}}</text>
						</view>
						<view class="" style="font-size: 24rpx;display:flex;flex:1;justify-content: center;">
							
						</view>
					</view>
					<view class="" style="display:flex;" v-if="item.device_class==3">
						<view class="" style="font-size: 28rpx;width:70%">
							<text style="width:150rpx;display:inline-block">每日收益:</text>
							<text>{{item.nfc_num}}</text>
						</view>
						<view class="" style="font-size: 24rpx;display:flex;flex:1;justify-content: center;">
							
						</view>
					</view>
					<view class="" style="display:flex;justify-content: flex-end;margin-top:20rpx">
						<view class="" style="font-size: 24rpx;display:flex;justify-content: center;width:30%;">
							<text style="color:white;font-weight:bold;display:inline-block;background:#817ef4;border-radius:30rpx;padding:2rpx 30rpx" v-if="item.device_class==3">租用</text>
							<text style="color:white;font-weight:bold;display:inline-block;background:#817ef4;border-radius:30rpx;padding:2rpx 30rpx" v-else>托管</text>
						</view>
					</view> -->

			</view>

			<uni-load-more :status="pageLoadingStatus" class="xt">
				<view slot="nodatatmp" class="nodata" style="margin-top:30rpx;font-size:30rpx">
					<text>暂无数据</text>
				</view>
			</uni-load-more>
		</view>
		
	</view>
</template>

<script>
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
				pageSize: 15, //页面数量	
				pageListData: [],

				lowmbpoit: "",
				pageLoadingStatus: '',
				fhStatus: [],
				cltype: "",
				current: true,
				host: uni.getStorageSync("myValue") + uni.getStorageSync("name") + "/api",
				pH: 0, //窗口高度
				navHeight: 0, //元素的所需高度
				myValue: "",
				cur: ""
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

			let that = this;
			uni.getSystemInfo({ //调用uni-app接口获取屏幕高度
				success(res) { //成功回调函数
					that._data.pH = res.windowHeight //windoHeight为窗口高度，主要使用的是这个
					let titleH = uni.createSelectorQuery().select(".sv"); //想要获取高度的元素名（class/id）
					// titleH.boundingClientRect(data => {
					// 	let pH = that._data.pH;
					// 	that._data.navHeight = pH - data.top-50 //计算高度：元素高度=窗口高度-元素距离顶部的距离（data.top）
					// }).exec()
					console.log(titleH)
				}
			})
		},
		onShow() {
			this.fhStatus = []
			this.loadData()
			this.pageNum = 1;
			this.loadPageData();
			this.menuShow = false
		},
		methods: {
			async search() {
				// this.sc.goTo('/pages/device/deviceIncome/deviceIncome')
				this.myValue.replace(" ", "")
				let result = await this.sc.callApi({
					data: {
						devicesn: this.myValue,
						device_class: this.cur
					},
					url: "/devices/Index",
					host: this.host
				});
				if (result.success) {
					// var title = this.cur == 3 ? '租用矿机' : '托管矿机'

					// this.pageNum = 1
					// this.pageListData = result.data.table.rows
					// if (result.data.table.total == 0) {
					// 	return this.sc.showMsg("当前没有搜索到匹配的" + title)
					// }
					if (this.myValue == '') {
						this.pageNum = 1
						this.loadPageData()
					}
				}
			},
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
					url: "/devices/type",
					host: this.host
				});
				this.fhStatus.push("所有")
				if (result.success) {
					console.log(result.data)
					this.cltype = result.data

					this.cltype.forEach((item, index) => {
						this.fhStatus.push(item.name)
					})
				}

			},
			getKuangji(type) {
				this.cur = type
				this.pageNum = 1
				this.loadPageData()
			},
			async loadPageData() {

				this.pageLoadingStatus = 'loading';
				var pdata = {
					pageNum: this.pageNum,
					pageSize: this.pageSize,
					online_status: this.index ? this.index : "",
					lowmbpoit: this.current ? "" : "1",
					typeid: this.index2 ? this.cltype[this.index2 - 1].id : "",
					// device_class: this.cur
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
	.current {
		border-bottom: 2px solid #817ef4;
		color: #817ef4
	}

	.mar {
		top: var(--window-top)
	}

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

	.tall {
		height: 280rpx
	}

	.short {
		height: 140rpx
	}
</style>
