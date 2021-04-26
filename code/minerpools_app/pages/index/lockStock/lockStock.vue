<template>
	<view>
		<!-- <view class="" style="display:flex;background:#f5f5f5;align-items: center;padding:20rpx 40rpx;position:width:100%;">
			<picker style="display:flex;align-items: center;background:white;width:200rpx;height:60rpx;border-radius:40rpx;"  :range="array" @change="bindPickerChange">
				<view style="font-size:24rpx;display:flex;justify-content: space-around;align-items: center;width:200rpx"><text style="color:#999999;">{{array[index]}}</text><image src="/static/bottom.png" mode="" style="width:20rpx;height:20rpx"></image></view>
				
			</picker>
			<view class="" style="display:flex;align-items: center;background:white;flex:1;height:60rpx;border-radius:40rpx;">
				<image src="/static/search.png" mode="" style="width:26rpx;height:26rpx;margin-left:28rpx"></image>
				<input type="text" v-model="myValue" style="height:60rpx;line-height: 60rpx;flex:1;margin-left:20rpx;font-size:26rpx" value=""
				 placeholder="请输入SN..." />
				  <image @click="myValue=''" v-if="myValue.length>0" src="/static/cha.png" mode="" style="width:26rpx;height:26rpx;margin-right:28rpx"></image>
			</view>
			<view class="" style="font-size:28rpx;color:#4285EC;padding-left:30rpx;height:60rpx;line-height: 60rpx;" @click="search">
				搜索
			</view>
			
		</view> -->
		<view class="" style="display:flex;background:#f5f5f5;align-items: center;padding:20rpx 40rpx;position:width:100%;">
			<view class="" style="display:flex;align-items: center;background:white;flex:1;height:60rpx;border-radius:40rpx;">
				<image src="/static/search.png" mode="" style="width:26rpx;height:26rpx;margin-left:28rpx"></image>
				<input type="text" v-model="myValue" style="height:60rpx;line-height: 60rpx;flex:1;margin-left:20rpx;font-size:26rpx" value=""
				 placeholder="请输入SN..." />
				 <image @click="myValue=''" v-if="myValue.length>0" src="/static/cha.png" mode="" style="width:26rpx;height:26rpx;margin-right:28rpx"></image>
			</view>
			<view class="" @click="search" style="font-size:28rpx;color:#757bff;padding-left:30rpx;height:60rpx;line-height: 60rpx;">
				搜索
			</view>
		</view>
		<view class="">
			<view v-for="(item,index) in pageListData" :key="index" class="" style="padding:0 20rpx;box-sizing: border-box;display:flex;flex-direction: column;justify-content: center;width: 694rpx;height: 250rpx;background: #FFFFFF;box-shadow: 0px 7px 21px 0px rgba(0, 52, 157, 0.1);border-radius: 11px;margin:20rpx auto">
				<view class="" style="display:flex;justify-content: space-between;font-size: 28rpx;margin:4rpx;margin-left:10rpx">
					<view class="">
						<text style="display:inline-block;width:180rpx">设备SN:</text>
						<text style="display:inline-block">{{item.devicesn}}</text>
					</view>
					<!-- <view class=""  style="margin-right:10rpx;color:#4285EC;font-size:24rpx">
						未提币
					</view>
					<view class="" style="margin-right:10rpx;color:#4285EC">
						提取申请中
					</view>
					<view class="" style="margin-right:10rpx;color:green">
						提币完成
					</view>
					<view class="" style="margin-right:10rpx;color:red">
						提币失败
					</view> -->
				</view>
				<view class="" style="font-size: 24rpx;color:#b9b9b9;margin:10rpx;margin-left:10rpx">
					<text style="display:inline-block;width:180rpx">总收益:</text>
					<text style="display:inline-block">{{item.sharesum}}</text>
				</view>
				<view class="" style="font-size: 24rpx;color:#b9b9b9;margin:10rpx;margin-left:10rpx">
					<text style="display:inline-block;width:180rpx">释放金额:</text>
					<text style="display:inline-block">{{item.hasrelease}}</text>
				</view>
				<view class="" style="font-size: 24rpx;color:#b9b9b9;margin:10rpx;margin-left:10rpx">
					<text style="display:inline-block;width:180rpx">锁仓金额:</text>
					<text style="display:inline-block">{{item.locknfc}}</text>
				</view>
				<!-- <view class="" style="font-size: 24rpx;color:#b9b9b9;margin:10rpx;margin-left:10rpx">
					<text style="display:inline-block;width:180rpx">释放时间:</text>
					<text style="display:inline-block">SN12312312313</text>
				</view>
				<view class="" style="font-size: 24rpx;color:#b9b9b9;margin:10rpx;margin-left:10rpx">
					<text style="display:inline-block;width:180rpx">分成时间:</text>
					<text style="display:inline-block">SN12312312313</text>
				</view> -->
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				index:0,
				array: ["所有记录", "未提币", "提币申请中", "提币完成", "提币失败"],
				pageNum: 1, //查询页面
				pageSize: 10, //页面数量	
				pageListData: [],
				pageLoadingStatus: '',
				cltype: "",
				host: uni.getStorageSync("myValue")+uni.getStorageSync("name")+"/api",
				myValue:""
			}
		},
		onShow() {
			this.pageNum = 1;
			this.loadPageData();
		},
		methods: {
			
			async search(){
				this.myValue.replace(" ", "")
				let result = await this.sc.callApi({
					data:{
						devicesn:this.myValue
					},
					url: "/Draw/locklist",
					host: this.host,
					method:"POST",
					host: this.host
				});
				if(result.success){
					this.pageNum=1
					this.pageListData=result.data.table.rows
					if(this.myValue==''){
						this.loadPageData()
					}
				}
			},

			bindPickerChange: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.index = e.target.value
			},
			async loadPageData() {
			
				this.pageLoadingStatus = 'loading';
				var pdata = {
					pageNum: this.pageNum,
					pageSize: this.pageSize,
				}
				let result = await this.sc.callApi({
					data:pdata,
					url: "/Draw/locklist",
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

</style>
