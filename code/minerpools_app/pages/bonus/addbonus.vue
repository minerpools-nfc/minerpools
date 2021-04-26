<template>
	<view class="">
		<view class="jifen" >
			<view class="line">
				<view class="myTitle" >
					策略名称
				</view>
				<input class="myInput" type="text" v-model="policy_name" placeholder="编辑策略名称"/>
			</view>
			<view class="line">
				<view class="myTitle" >
					策略描述
				</view>
				<textarea class="" v-model="policy_desc"   style="height:70rpx;width:100%;margin-left: 110rpx;font-size: 26rpx;" placeholder="编辑策略描述"/>
			</view>
			<view class="line">
				<view class="myTitle" >
					分红总额
				</view>
				<input class="myInput" type="number" v-model="total_num" placeholder="编辑分红总额"/>
			</view>
			<view class="line" style="">
				<view class="myTitle">
					分红类型
				</view>
				<radio-group @change="change" :value="policy_type" style="vertical-align: middle;">
					<label class="radio" style="margin-left: 20upx;font-size:28rpx;font-weight:bold;">
						<radio style="transform:scale(0.7)" value="1" checked="true" />平均分配
					</label>
					<label class="radio" style="margin-left: 20upx;font-size:28rpx;font-weight:bold;">
						<radio style="transform:scale(0.7)" value="2" />按流量贡献比例
					</label>
				</radio-group>
			</view>
			<view class="line" style="">
				<view class="myTitle" >
					开始时间
				</view>
				<picker mode="date" fields="day" :value="startime" @change="bindDateChange" style="width:160rpx;margin-left:60rpx;font-size:28rpx;font-weight:bold;display:flex;height:70rpx;align-items: center">
					<view class="uni-input">{{startime}}</view>
				</picker>
			</view>
			<view class="line" style="">
				<view class="myTitle">
					结束时间
				</view>
				<picker mode="date" fields="day" :value="endtime" @change="bindDateChange2" style="width:160rpx;margin-left:60rpx;font-size:28rpx;font-weight:bold;display:flex;height:70rpx;align-items: center">
					<view class="uni-input">{{endtime}}</view>
				</picker>
			</view>
			<view class="line" style="">
				<view class="myTitle">
					启用状态
				</view>
				<radio-group @change="change2" :value="policy_status" style="vertical-align: middle;font-size:28rpx;font-weight:bold;">
					<label class="radio" style="margin-left: 20upx;">
						<radio style="transform:scale(0.7)" value="1" checked="true" />启用
					</label>
					<label class="radio" style="margin-left: 20upx;">
						<radio style="transform:scale(0.7)" value="2" />不启用
					</label>
				</radio-group>
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
				data: "积分不足",
				index: 0,
				menuShow: false,
				array: ['中国', '美国', '巴西', '日本'],
				date: this.func.getDate(),
				
				policy_name:"",
				policy_type:1,
				policy_status:1,
				policy_desc:"",
				startime:this.func.getDate(),
				endtime:this.func.getDate(),
				total_num:"",
				host:uni.getStorageSync("name")+":"+uni.getStorageSync('dk')+"/api"

			}
		},
		methods: {
			bindDateChange: function(e) {
				// this.date = e.target.value
				this.startime=e.target.value
				console.log(e)
			},
			bindDateChange2: function(e) {
				// this.date = e.target.value
				this.startime=e.target.value
				console.log(e)
			},
			change(e) {
				this.policy_type=e.detail.value
			},
			change2(e) {
				this.policy_status=e.detail.value
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
				if(!this.valid(this.policy_name)){
					return this.sc.showMsg("策略名称不能为空")
				}
				if(!this.valid(this.total_num)){
					return this.sc.showMsg("策略描述不能为空")
				}
				if(!this.valid(this.policy_desc)){
					return this.sc.showMsg("分红总数不能为空")
				}
				let result = await this.sc.callApi({
					data:{
						policy_name:this.policy_name,
						policy_type:this.policy_type,
						policy_status:this.policy_status,
						policy_desc:this.policy_desc,
						startime:this.startime,
						endtime:this.endtime,
						total_num:this.total_num
					},
					url: "/DeviceDrawPolicy/add",
					method:"POST",
					host:this.host
				});
				if (result.success) {
					this.sc.showMsg("保存成功")
					setTimeout(uni.navigateBack({
						
					}),500)
				}
			},
		},
		onLoad() {

		}
	}
</script>

<style scoped>
	.line {
		display: flex;
		width: 86%;
		margin: 0 auto;
		height: 70rpx;
		align-items: center;
		/* border-bottom: 1px solid #EEEEEE */
	}
	
	.myInput {
		width: 100%;
		margin-left: 110rpx;
		font-size: 28rpx;
		font-weight: bold;
	}
	
	.myTitle {
		width: 200rpx;
		font-size: 30rpx;
		font-weight: bold
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
