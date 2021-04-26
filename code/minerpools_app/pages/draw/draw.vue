<template>
	<view class="content" style="height:100%">
		<!-- position:fixed -->
		<view class="purchase-body">
			<view class=""
				style="position:relative;background:#f5f5f5;width:100%;display:flex;padding:32rpx 30rpx 80rpx 30rpx;justify-content: space-around;box-sizing: border-box;flex-wrap: wrap;">
				<view class=""
					style="width:40%;height:112rpx;background:white;border-radius: 8rpx;display:flex;flex-direction: column;justify-content: space-around;align-items: center;">
					<view class="" style="color:#757BFF;font-size:26rpx">
						锁仓
					</view>
					<view class="" style="color:black;font-size:24rpx;font-weight:bold">
						{{cltype.my_locknfc}}
					</view>
				</view>
				<view class=""
					style="width:40%;height:112rpx;background:white;border-radius: 8rpx;display:flex;flex-direction: column;justify-content: space-around;align-items: center;">
					<view class="" style="color:#757BFF;font-size:26rpx">
						待提取
					</view>
					<view class="" style="color:black;font-size:24rpx;font-weight:bold">
						{{cltype.my_waitdraw}}
					</view>
				</view>
				<view class=""
					style="margin-top:20rpx;width:40%;height:112rpx;background:white;border-radius: 8rpx;display:flex;flex-direction: column;justify-content: space-around;align-items: center;">
					<view class="" style="color:#757BFF;font-size:26rpx">
						已提取
					</view>
					<view class="" style="color:#12BA05;font-size:24rpx;font-weight:bold">
						{{cltype.my_hasdraw}}
					</view>
				</view>
				<view class=""
					style="margin-top:20rpx;width:40%;height:112rpx;background:white;border-radius: 8rpx;display:flex;flex-direction: column;justify-content: space-around;align-items: center;">
					<view class="" style="color:#757BFF;font-size:26rpx">
						提取中
					</view>
					<view class="" style="color:#12BA05;font-size:24rpx;font-weight:bold">
						{{cltype.my_indraw}}
					</view>
				</view>
				<!-- <view class="" style="display:flex;background:#f5f5f5;align-items: center;padding:20rpx 40rpx;position:width:100%;;position:absolute;bottom:40rpx;width:90%">
					<view class="" @click="" style="font-size:24rpx;color:#757bff;;height:60rpx;line-height: 60rpx;">
						钱包地址：
					</view>
					<view class="" style="display:flex;align-items: center;background:white;flex:1;height:60rpx;border-radius:40rpx;">
						<input type="text" v-model="myValue" style="height:60rpx;line-height: 60rpx;flex:1;margin-left:20rpx;font-size:24rpx"
						 value="" placeholder="请输入钱包地址并提取..." />
					</view>
				</view> -->
				<button class=""
					style="font-size:26rpx;color:white;width:66%;background:#817ef4;height:72rpx;line-height:72rpx;border-radius:50rpx;position:absolute;bottom:-36rpx"
					@click="draw">申请提取</button>
			</view>
			<view class=""
				style="display:flex;align-items: center;padding:20rpx 40rpx;position:width:100%;padding-top:70rpx">
				<picker
					style="background:#f5f5f5;display:flex;align-items: center;width:200rpx;height:60rpx;border-radius:40rpx;"
					:range="array" @change="bindPickerChange">
					<view
						style="font-size:24rpx;display:flex;justify-content: space-around;align-items: center;width:200rpx">
						<text style="color:#999999;">{{array[index]}}</text>
						<image src="/static/bottom.png" mode="" style="width:20rpx;height:20rpx"></image>
					</view>

				</picker>
				<!-- <view class="" style="background:#f5f5f5;display:flex;align-items: center;flex:1;height:60rpx;border-radius:40rpx;margin-left:20rpx">
					<image src="/static/search.png" mode="" style="width:26rpx;height:26rpx;margin-left:28rpx"></image>
					<input type="text" style="height:60rpx;line-height: 60rpx;flex:1;margin-left:20rpx;font-size:26rpx" value=""
					 placeholder="请输入时间段..." />
				</view> -->
				<picker
					style="background:#f5f5f5;display:flex;align-items: center;width:180rpx;height:60rpx;border-radius:40rpx;justify-content: center;margin-left:10rpx"
					mode="date" :value="start_time" @change="getStartTime">
					<view
						style="font-size:24rpx;display:flex;justify-content: space-around;align-items: center;width:200rpx">
						<text style="color:#999999;">{{start_time}}</text></view>

				</picker>
				-
				<picker
					style="background:#f5f5f5;display:flex;align-items: center;width:180rpx;height:60rpx;border-radius:40rpx;;justify-content: center;"
					mode="date" fields="day" :value="end_time" @change="getEndTime">
					<view
						style="font-size:24rpx;display:flex;justify-content: space-around;align-items: center;width:200rpx">
						<text style="color:#999999;">{{end_time}}</text></view>

				</picker>
				<view @click="search" class=""
					style="font-size:28rpx;color:#4285EC;padding-left:30rpx;height:60rpx;line-height: 60rpx;">
					搜索
				</view>

			</view>
		</view>
		<!-- margin-top:590rpx -->
		<!-- <scroll-view scroll-y="true" class="table-content" :style="{height:height}" >
			<view v-for="(item,cd) in pageListData" :key="cd" style="width: 694rpx;background: #FFFFFF;box-shadow: 0rpx 8rpx 21rpx 0rpx rgba(0, 52, 157, 0.1);border-radius: 12rpx;margin:20rpx auto;display:flex;justify-content: center;box-sizing: border-box;flex-direction: column;padding:40rpx 50rpx">
				<view class="" style="font-size: 28rpx;">
					<text style="width:170rpx;display:inline-block">提取时间:</text>
					<text>{{item.addtime}}</text>
				</view>
				<view class="" style="display:flex;justify-content: space-between;font-size: 24rpx;color: #9e9e9e;align-items: center;">
					<view class=" style=""">
						<view class="" style="margin-top:20rpx;">
							<text style="width:170rpx;display:inline-block">提取编号:</text>
							<text>{{item.applyid}}</text>
						</view>
						<view class="" style="margin: 10rpx 0;">
							<text style="width:170rpx;display:inline-block">提取数量:</text>
							<text>{{item.share_num}}</text>
						</view>
						<view class="" style="margin: 10rpx 0;">
							<text style="width:170rpx;display:inline-block">钱包地址:</text>
							<text>{{item.wallet_address}}</text>
						</view>
						<view class="" style="margin: 10rpx 0;">
							<text style="width:170rpx;display:inline-block">备注:</text>
							<text>{{item.remark}}
							</text>
						</view>
					</view>
					<view class="" style="color:#4285EC">
						{{item.trans_status_label}}
					</view>
				</view>
			</view>

		</scroll-view> -->

		<scroll-view class="scview" scroll-y="true">
			<view v-for="(item,cd) in pageListData" :key="cd"
				style="position:relative;width: 694rpx;background: #FFFFFF;box-shadow: 0rpx 8rpx 21rpx 0rpx rgba(0, 52, 157, 0.1);border-radius: 12rpx;margin:20rpx auto;display:flex;justify-content: center;box-sizing: border-box;flex-direction: column;padding:20rpx 50rpx">
				<view class="" style="font-size: 28rpx;">
					<text style="width:170rpx;display:inline-block">提取时间:</text>
					<text>{{item.addtime}}</text>
				</view>
				<view class=""
					style="display:flex;justify-content: space-between;font-size: 24rpx;color: #9e9e9e;align-items: center;">
					<view class=" style=""">
						<view class="" style="margin-top:20rpx;">
							<text style="width:170rpx;display:inline-block">提取编号:</text>
							<text>{{item.applyid}}</text>
						</view>
						<view class="" style="margin: 10rpx 0;">
							<text style="width:170rpx;display:inline-block">提取数量:</text>
							<text>{{item.share_num}}</text>
						</view>
						<view class="" style="margin-top:10rpx;word-break:break-all; display:flex">
							<text style="width:170rpx;display:inline-block">钱包地址:</text>
							<text style="flex:1">{{item.wallet_address}}</text>
						</view>
					</view>
					<view class="" style="color:#4285EC;position:absolute;right:20rpx;top:20rpx;font-weight:bold">
						{{item.trans_status_label}}
					</view>
				</view>
			</view>

			<uni-load-more :status="pageLoadingStatus" class="xt">
				<view slot="nodatatmp" class="nodata" style="margin-top:30rpx;font-size:30rpx">
					<text>暂无数据</text>
				</view>
			</uni-load-more>
		</scroll-view>



	</view>
</template>

<script>
	var that;
	export default {
		data() {
			return {
				flag: true,
				index: 0,

				height: null, //获取的状态栏高度
				pageNum: 1, //查询页面
				pageSize: 8, //页面数量	
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
				array: ["提取状态", "申请中", "已完成"],
				start_time: "开始时间",
				end_time: "结束时间",
				wallet_address: ""
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

		},
		onShow() {
			this.fhStatus = []
			this.loadData()
			this.pageNum = 1;
			this.loadPageData();
			this.menuShow = false
			var _this = this;
			uni.getSystemInfo({
				success: (resu) => {
					const query = uni.createSelectorQuery()
					query.select('.purchase-body').boundingClientRect()
					query.exec(function(res) {
						_this.height = resu.windowHeight - res[0].top + 'px';
						console.log('打印页面的剩余高度', _this.height);
					})
					console.log(resu)
				},
				fail: (res) => {}
			})
		},
		methods: {
			goMe() {
				uni.switchTab({
					url: '/pages/my/my'
				});
			},
			async draw() {
				var that = this
				let result = await this.sc.callApi({
					url: "/draw/dodraw ",
					// data: {
					// 	wallet_address: this.myValue
					// },
					host: this.host,
					method: "POST"
				})
				if (result.code == 5) {
					setTimeout(that.goMe, 1000)
				}
				if (result.success) {
					console.log(result.code)
					this.pageNum = 1
					this.loadPageData()
					this.loadData()
					console.log(result.data)
				}
			},
			async search() {
				this.pageNum = 1
				this.pageLoadingStatus = 'loading';
				let result = await this.sc.callApi({
					data: {
						pageNum: this.pageNum,
						pageSize: this.pageSize,
						trans_status: this.index ? this.index : "",
						start_time: this.start_time == "开始时间" ? "" : this.start_time,
						end_time: this.end_time == "结束时间" ? "" : this.end_time
					},
					url: "/Draw/index",
					host: this.host
				});
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

			bindPickerChange: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.index = e.target.value
			},
			getStartTime: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.start_time = e.target.value
			},
			getEndTime: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.end_time = e.target.value
			},
			go(url) {
				console.log(11)
				uni.navigateTo({
					url

				});
			},
			async loadData() {
				let result = await this.sc.callApi({
					url: "/draw/appstatic",
					host: this.host
				});
				if (result.success) {
					this.cltype = result.data
					this.myValue = result.data.my_wallet_address
					console.log(result.data)
				}
				let result2 = await this.sc.callApi({
					url: "/BindUsers/myinfo",
					host: this.host
				});
				if (result2.success) {
					this.wallet_address = result.data.wallet_address
					console.log(result2.data)
				}
			},
			async loadPageData() {

				this.pageLoadingStatus = 'loading';
				var pdata = {
					pageNum: this.pageNum,
					pageSize: this.pageSize,

				}

				let result = await this.sc.callApi({
					data: pdata,
					url: "/Draw/index",
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
	.scview {
		/* height:calc(100vh - 510rpx - 100rpx - 88rpx);  */
		/* #ifdef H5 */
		height: calc(100vh - 88rpx - 100rpx - 510rpx - env(safe-area-inset-bottom) - var(--status-bar-height));
		/* #endif */
		/* #ifdef APP-PLUS */
		height: calc(100vh - 88rpx - 510rpx - env(safe-area-inset-bottom));
		/* #endif */
	}
</style>
