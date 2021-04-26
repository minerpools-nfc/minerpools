<template>
	<view class="content">

		<view class="" style="display:flex;background:#f5f5f5;height:80rpx;align-items: center;padding:20rpx 40rpx;width:100%;justify-content: space-between;box-sizing: border-box;">
			<view class="" style="font-size:30rpx;font-weight:bold">
				设备SN: {{devicesn}}
			</view>
			<view class="" style="font-size: 24rpx;">
				<text style="display:inline-block;margin-right:10rpx">状态:</text>
				<text style="color:#49b600" v-if="devicesnList.online_status==1">在线</text>
				<text style="color:red" v-else-if="devicesnList.online_status==0">离线</text>
			</view>
		</view>
		<view class="" style="padding:0 40rpx;font-size:26rpx;color:#9e9e9e">
			<view class="">
				<text style="display:inline-block;margin-right:20rpx;margin:10rpx 10rpx 10rpx 0;width:150rpx">总流量:</text>
				<text style="">{{devicesnList.flow_bill_all}}</text>
			</view>
			<view class="">
				<text style="display:inline-block;margin-right:20rpx;margin:10rpx 10rpx 10rpx 0;width:150rpx">设备地址:</text>
				<text style="">{{devicesnList.device_localtion}}</text>
			</view>
			<view class="">
				<text style="display:inline-block;margin-right:20rpx;margin:10rpx 10rpx 10rpx 0;width:150rpx">总收益:</text>
				<text style="">{{devicesnList.flow_bill_all}}</text>
			</view>
			<view class="">
				<text style="display:inline-block;margin-right:20rpx;margin:10rpx 10rpx 10rpx 0;width:150rpx">总释放:</text>
				<text style="">{{devicesnList.sharenum_all}}</text>
			</view>

			<!-- <view class="" v-if="devicesnList.online_status==1">
				<text style="width:170rpx;display:inline-block">最后在线时间:</text>
				<text>{{devicesnList.Online_time}}</text>
			</view>
			<view class="" v-else-if="devicesnList.online_status==0">
				<text style="width:170rpx;display:inline-block">最后离线时间:</text>
				<text>{{devicesnList.offline_time}}</text>
			</view> -->
		</view>
		<view class="" style="display:flex;align-items: center;padding:20rpx 40rpx;position:width:100%;padding-top:70rpx;justify-content: space-around;">
			<picker style="background:#f5f5f5;display:flex;align-items: center;width:180rpx;height:60rpx;border-radius:40rpx;justify-content: center;margin-left:10rpx;margin-right:60rpx"
			 mode="date" :value="start_date" @change="getStartTime">
				<view style="font-size:24rpx;display:flex;justify-content: space-around;align-items: center;width:200rpx"><text
					 style="color:#999999;">{{start_date}}</text></view>

			</picker>
			-
			<picker style="background:#f5f5f5;display:flex;align-items: center;width:180rpx;height:60rpx;border-radius:40rpx;;justify-content: center;margin-left:60rpx"
			 mode="date" fields="day" :value="end_date" @change="getEndTime">
				<view style="font-size:24rpx;display:flex;justify-content: space-around;align-items: center;width:200rpx"><text
					 style="color:#999999;">{{end_date}}</text></view>

			</picker>
			<view @click="search" class="" style="font-size:28rpx;color:#4285EC;padding-left:30rpx;height:60rpx;line-height: 60rpx;">
				搜索
			</view>

		</view>
		<echarts v-if="this.option.series[0].data.length" :option="option" style="height: 320px;" @click="echartsClick"></echarts>
	</view>
</template>

<script>
	import echarts from '@/components/echarts/echarts.vue'
	export default {
		data() {
			return {
				toolTipData: [],
				start_date: "开始时间",
				end_date: "结束时间",
				flag: true,
				devicesn: "",
				devicesnList: '',
				fhStatus: [],
				index: 0,
				flag: true,
				cltype: "",
				host: uni.getStorageSync("name") + ":" + uni.getStorageSync('dk') + "/api",
				option: {
					// xAxis: [{
					// 	name: '位移（m）',
					// 	nameLocation: 'middle', //轴位置
					// 	nameGap: 26, //name名字与轴线间距
					// 	type: 'value',
					// 	axisLine: {
					// 		onZero: false
					// 	},
					// 	splitLine:{show: false}
					// }],
					// yAxis: [{
					// 	name: '载荷（KN）',
					// 	nameLocation: 'middle', //轴位置
					// 	nameGap: 26, //name名字与轴线间距
					// 	type: 'value',
					// 	axisLine: {
					// 		onZero: false
					// 	}
					// }],
					// tooltip: {
					// 	trigger: 'axis'
					// },

					tooltip: {
						trigger: 'axis',
						// borderWidth:1,
						// borderColor:"blue",
						// extraCssText:"box-shadow:0px 0px 10px 0px rgba(4,0,0,0.3)",
						// backgroundColor:'rgba(255,255,255,0.9)',
						// textStyle:{
						// 	fontSize:12,
						// 	color:"red"
						// },
						formatter:function(params){
							console.log(params)
							return (
								`流量：${params[0].value}GB 已释放：${params[1].value} 收益：${params[2].value}`
							)
						}

					},
					legend: {
						// data: ['流量', '已释放', '收益'],
						data: [''],
						// selected:{'总收益':false, '锁仓':false, '释放':false}
					},

					grid: {
						left: '8%',
						right: '8%',
						bottom: '8%',
						containLabel: true,
						// width:"600rpx"
					},
					// toolbox: {
					//     feature: {
					//         saveAsImage: {}
					//     }
					// },
					xAxis: {
						axisLabel: {
							show: true,
							textStyle: {
								fontSize: 10 //更改坐标轴文字大小
							}
						},
						axisLine: {
							onZero: false
						},
						nameGap: 26, //name名字与轴线间距
						nameLocation: 'middle', //轴位置
						name: '日期',
						type: 'category',
						boundaryGap: false,
						data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日', '周一', '周二', '周三', '周四', '周五', '周六', '周日', '周一', '周二', '周三', '周四',
							'周五', '周六', '周日', '周一', '周二', '周三', '周四', '周五', '周六', '周日'
						]
					},
					yAxis: {
						axisLabel: {
							show: true,
							textStyle: {
								fontSize: 10 //更改坐标轴文字大小
							}
						},
						axisLine: {
							onZero: false
						},
						nameGap: 30, //name名字与轴线间距
						nameLocation: 'middle', //轴位置
						name: 'NfC',
						type: 'value'
					},
					series: [{
							name: '流量',
							type: 'line',
							stack: '总量',
							data: [],
							color: ['red'], //折线条的颜色
						},
						{
							name: '已释放',
							type: 'line',
							stack: '总量',
							data: [220, 182, 191, 234, 290, 330, 310],
							color: ['blue']
						},
						{
							name: '收益',
							type: 'line',
							stack: '总量',
							data: [150, 232, 201, 154, 190, 330, 410],
							color: ['green']
						}
					]
				}
			}
		},
		components: {
			echarts
		},
		onLoad(e) {
			this.devicesn = e.devicesn
		},
		onShow() {
			this.fhStatus = []
			this.loadData()
			this.loadData2()
		},
		methods: {
			async search() {
				let result = await this.sc.callApi({
					url: "/devices/recordapp",
					data: {
						start_date: this.start_date == "开始时间" ? "" : this.start_date,
						end_date: this.end_date == "结束时间" ? "" : this.end_date,
						devicesn: this.devicesn

					}
				})
				if (result.success) {
					console.log(result.data)
					if (result.data.flowbill_list.length == 0) {
						this.sc.showMsg("暂无数据")
						this.option.xAxis.data = result.data.date_list
						this.option.series[0].data = result.data.flowbill_list
						this.option.series[1].data = result.data.release_list
						this.option.series[2].data = result.data.sharenum_list
					} else {
						this.option.xAxis.data = result.data.date_list
						this.option.series[0].data = result.data.flowbill_list
						this.option.series[1].data = result.data.release_list
						this.option.series[2].data = result.data.sharenum_list
					}
				}
			},
			getStartTime: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.start_date = e.target.value
			},
			getEndTime: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.end_date = e.target.value
			},
			echartsClick(params) {
				console.log('点击数据', params)
			},
			valid(value) {
				value = value.replace(/\s+/g, "");
				if (value == '') {
					return false;
				} else {
					return true;
				}
			},
			change() {

			},

			bindPickerChange: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.flag = false
				this.index = e.target.value
				this.devicesnList.typeid = null
				console.log(this.cltype[this.index].policyid)
			},
			filterA(item) {
				if (item == 1) {
					return "在线"
				} else {
					return "离线"
				}
			},

			async loadData() {
				let result = await this.sc.callApi({
					data: {
						devicesn: this.devicesn,
						// start_date:"2020-9-01",
						// end_date:"2020-10-01"
					},
					url: "/devices/recordapp"
				});
				if (result.success) {
					this.option.xAxis.data = result.data.date_list
					this.option.series[0].data = result.data.flowbill_list
					this.option.series[1].data = result.data.release_list
					this.option.series[2].data = result.data.sharenum_list
					console.log(result.data)

					// for(var i=0;i<result.data.flowbill_list.length;i++){
					// 	var toolObj={
					// 		flowbill_list:"",  //流量
					// 		release_list:"",  //已释放
					// 		sharenum_list:"",  //收益
					// 	}
					// 	toolObj.flowbill_list=result.data.flowbill_list[i];
					// 	toolObj.release_list=result.data.release_list[i];
					// 	toolObj.sharenum_list=result.data.sharenum_list[i]
					// 	this.toolTipData.push(toolObj)
					// }
					// console.log(this.toolTipData)
					// this.option.series[0].data=this.toolTipData
					// this.toolTipData
				}



			},
			async loadData2() {



				let result2 = await this.sc.callApi({
					url: "/devices/apprecordall",
					data: {
						devicesn: this.devicesn
					},
					host: this.host
				})
				if (result2.success) {
					this.devicesnList = result2.data
				}
			},
		}
	}
</script>

<style lang="scss">
	.head {
		display: flex;
		justify-content: space-between;
		padding: 0 2%;
		line-height: 40px;
		align-items: center;
		position: relative;
		background: #08b6f2;
		color: white
	}


	.shebeiContent {
		// margin:0 4%;
		padding: 20rpx;

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

	.plaClass {
		color: #dbdbdb;
	}

	.container999 {
		.title {
			height: 80rpx;
			line-height: 80rpx;
			padding-left: 4%;
			border-bottom: 1px solid #f5f5f5;
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

		.tri {
			width: 0;
			height: 0;
			border-left: 8rpx solid transparent;
			border-right: 8rpx solid transparent;
			right: 30rpx !important;
			border-top: 16rpx solid #545151;
		}

		.line2 {
			margin-top: 30rpx;
			height: 50rpx;
			width: 92%;
			margin: 0 auto;
			// border-bottom: 1px solid #f5f5f5;
			display: flex;

			.lineRight {
				.tips {
					color: #9a9a9c;
					font-weight: bold;
				}

				flex: 1;
				height: 100%;
				display: flex;
				align-items: center;
				justify-content: space-between;
				position: relative;
			}

			.lineLeft {
				display: flex;
				width: 30%;
				align-items: center;
				height: 100%;
				font-weight: bold;
				color: #000000;
			}

			.input {
				padding-right: 20rpx;
				height: 100%;
				width: 60%;
				text-align: left;
				font-size: 28rpx;
				color: #545151;
				padding-left: 20rpx
			}

			.picker {
				height: 100%;
				justify-content: flex-start;
				display: flex;
				align-content: center;
				width: 500rpx;
			}

			picker {
				height: 84rpx;
				line-height: 84rpx;
			}
		}

		.line {
			margin-top: 0rpx;
			height: 100rpx;
			width: 92%;
			margin: 0 auto;
			border-bottom: 1px solid #f5f5f5;
			display: flex;

			.lineRight {
				.tips {
					color: #9a9a9c;
					font-weight: bold;
				}

				flex: 1;
				height: 100%;
				display: flex;
				align-items: center;
				justify-content: space-between;
				position: relative;
			}

			.lineLeft {
				display: flex;
				width: 30%;
				align-items: center;
				height: 100%;
				font-weight: bold;
				color: #000000;
			}

			.input {
				padding-right: 20rpx;
				height: 100%;
				width: 60%;
				text-align: left;
				font-size: 28rpx;
				color: #545151;
				padding-left: 20rpx
			}

			.picker {
				height: 100%;
				justify-content: flex-start;
				display: flex;
				align-content: center;
				width: 500rpx;
			}

			picker {
				height: 84rpx;
				line-height: 84rpx;
			}
		}

		width: 100vw;
		font-size: 28upx;
		min-height: 100vh;
		overflow: hidden;
		color: #6b8082;
		position: relative;
	}
</style>
