<template>
	<view>
		<!-- <view class="mar" style="background:#f5f5f5;display:flex;align-items: center;padding:20rpx 40rpx;position:fixed;width:100%;justify-content: space-around;box-sizing: border-box;">
			<picker style="background:white;display:flex;align-items: center;width:220rpx;height:60rpx;border-radius:40rpx;justify-content: center;margin-left:10rpx;margin-right:60rpx"
			 mode="date" :value="start_date" @change="getStartTime">
				<view style="font-size:24rpx;display:flex;justify-content: space-around;align-items: center;width:220rpx"><text
					 style="color:#999999;">{{start_date}}</text>
					<image src="/static/bottom.png" mode="" style="width:20rpx;height:20rpx"></image>
				</view>
			</picker>
			-
			<picker style="background:white;display:flex;align-items: center;width:220rpx;height:60rpx;border-radius:40rpx;;justify-content: center;margin-left:60rpx"
			 mode="date" fields="day" :value="end_date" @change="getEndTime">
				<view style="font-size:24rpx;display:flex;justify-content: space-around;align-items: center;width:220rpx"><text
					 style="color:#999999;">{{end_date}}</text>
					<image src="/static/bottom.png" mode="" style="width:20rpx;height:20rpx"></image>
				</view>
			</picker>
			<view @click="loadPageData(1)" class="" style="font-size:28rpx;color:#4285EC;padding-left:30rpx;height:60rpx;line-height: 60rpx;">
				搜索
			</view>
		
		</view> -->
		<view class="mar" style="display:flex;background:#f5f5f5;align-items: center;padding:20rpx 40rpx;position:fixed;width:100%;box-sizing: border-box;z-index:99">
			<view class="" style="display:flex;align-items: center;background:white;flex:1;height:60rpx;border-radius:40rpx;">
				<image src="/static/search.png" mode="" style="width:26rpx;height:26rpx;margin-left:28rpx"></image>
				<input type="text" v-model.trim="myValue" style="height:60rpx;line-height: 60rpx;flex:1;margin-left:20rpx;font-size:26rpx" value=""
				 placeholder="请输入SN..." />
				 <image @click="myValue=''" v-if="myValue.length>0" src="/static/cha.png" mode="" style="width:26rpx;height:26rpx;margin-right:28rpx"></image>
			</view>
			<view class="" @click="loadPageData(1)" style="font-size:28rpx;color:#757bff;padding-left:30rpx;height:60rpx;line-height: 60rpx;">
				搜索
			</view>
		</view>
		<view class="" style='margin-top:110rpx;padding-bottom: 100rpx;'>
			<!-- <view v-for="(item,index) in pageListData" :key="index" class="" style="padding:0 20rpx;box-sizing: border-box;display:flex;flex-direction: column;justify-content: center;width: 90%;height: 250rpx;background: #FFFFFF;box-shadow: 0px 7px 21px 0px rgba(0, 52, 157, 0.1);border-radius: 11px;margin:20rpx auto">
				<view class="" style="display:flex;justify-content: space-between;font-size: 28rpx;margin:4rpx;margin-left:10rpx">
					<view class="">
						<text style="display:inline-block;width:180rpx">矿机SN:</text>
						<text style="display:inline-block">{{item.devicesn}}</text>
					</view>
					
				</view>
				<view class="" style="font-size: 24rpx;color:#b9b9b9;margin:10rpx;margin-left:10rpx">
					<text style="display:inline-block;width:180rpx">时间:</text>
					<text style="display:inline-block">{{item.ctime}}</text>
				</view>
				<view class="" style="font-size: 24rpx;color:#b9b9b9;margin:10rpx;margin-left:10rpx">
					<text style="display:inline-block;width:180rpx">收益:</text>
					<text style="display:inline-block">{{item.sharenum}}</text>
				</view>
				<view class="" style="font-size: 24rpx;color:#b9b9b9;margin:10rpx;margin-left:10rpx">
					<text style="display:inline-block;width:180rpx">待提取数量:</text>
					<text style="display:inline-block">{{item.user_locknum}}</text>
				</view>
			</view> -->
			<view v-for="(item,index) in pageListData" :key="index" class="" style="padding:0 20rpx;box-sizing: border-box;display:flex;flex-direction: column;justify-content: center;width: 90%;height: 250rpx;background: #FFFFFF;box-shadow: 0px 7px 21px 0px rgba(0, 52, 157, 0.1);border-radius: 11px;margin:20rpx auto">
				<view class="" style="display:flex;justify-content: space-between;font-size: 28rpx;margin:4rpx;margin-left:10rpx">
					
					<view class="">
						<text style="display:inline-block;width:180rpx">矿机SN:</text>
						<text style="display:inline-block">{{item.devicesn}}</text>
					</view>
					
				</view>
				<view class="" style="font-size: 24rpx;color:#b9b9b9;margin:10rpx;margin-left:10rpx">
					<text style="display:inline-block;width:180rpx">时间:</text>
					<text style="display:inline-block">{{item.ctime}}</text>
				</view>
				<view class="" style="font-size: 24rpx;color:#b9b9b9;margin:10rpx;margin-left:10rpx">
					<text style="display:inline-block;width:180rpx">收益:</text>
					<text style="display:inline-block">{{item.sharenum}}</text>
				</view>
				<view class="" style="font-size: 24rpx;color:#b9b9b9;margin:10rpx;margin-left:10rpx">
					<text style="display:inline-block;width:180rpx">待提取数量:</text>
					<text style="display:inline-block">{{item.waitdraw}}</text>
				</view>
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
	export default {
		data() {
			return {
				index:0,
				pageNum: 1, //查询页面
				pageSize: 10, //页面数量	
				pageListData: [],
				pageLoadingStatus: '',
				cltype: "",
				host: uni.getStorageSync("myValue")+uni.getStorageSync("name")+"/api",
				myValue:"",
				
				start_date: "开始时间",
				end_date: "结束时间",
			}
		},
		onShow() {
			this.pageNum = 1;
			this.loadPageData();
		},
		methods: {
			
			
			// async search(){
			// 	let result = await this.sc.callApi({
			// 		url: "/draw/waitdraw",
			// 		data: {
			// 			start_date: this.start_date == "开始时间" ? "" : this.start_date,
			// 			end_date: this.end_date == "结束时间" ? "" : this.end_date,
			// 			pageNum: this.pageNum,
			// 			pageSize: this.pageSize,
			// 		},
			// 		method: "POST",
			// 		host: this.host
			// 	})
			// 	let pageList = [];
			// 	if (result.success) {
			// 		pageList = result.data.table.rows;
			// 		if (pageList) {
			// 			// console.log(pageList)
			// 		} else {
			// 			pageList = [];
			// 		}
			// 	}
			// 	if (this.pageNum == 1 && pageList.length == 0) {
			// 		this.pageListData = [];
			// 		this.pageLoadingStatus = 'nodata';
			// 		return;
			// 	}
			// 	this.pageListData = this.pageNum == 1 ? pageList : this.pageListData.concat(pageList);
			// 	this.pageLoadingStatus = pageList.length < this.pageSize ? 'nomore' : 'more';
			// },
			
			
			// async search(){
			// 	this.myValue.replace(' ','')
			// 	if(this.myValue==""){
			// 		this.pageNum=1
			// 		this.loadPageData()
			// 	}
			// 	let result = await this.sc.callApi({
			// 		url: "/draw/waitdraw",
			// 		data: {
			// 			devicesn:this.myValue
			// 		},
			// 		method: "POST",
			// 		host: this.host
			// 	})
			// 	if (result.success) {
			// 		this.pageListData=result.data.table.rows
			// 	}
				
			// },
			bindPickerChange: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.index = e.target.value
			},
			getStartTime: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.start_date = e.target.value
			},
			getEndTime: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.end_date = e.target.value
			},
			async loadPageData(flag) {
				this.myValue.replace(' ','')
				if(flag){
					this.pageNum=1
				}
				this.pageLoadingStatus = 'loading';
				var pdata = {
					// start_date: this.start_date == "开始时间" ? "" : this.start_date,
					// end_date: this.end_date == "结束时间" ? "" : this.end_date,
					devicesn:this.myValue,
					pageNum: this.pageNum,
					pageSize: this.pageSize,
				}
				let result = await this.sc.callApi({
					data:pdata,
					url: "/draw/waitdraw",
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

<style>
.mar{
	top:var(--window-top)
}
</style>
