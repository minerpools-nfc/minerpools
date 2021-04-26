<template>
	<view class="content" >

		<view class="container999">
			<view class="shebeiContent">
				<view class='line2'>
					<view class='lineLeft'><!-- <text style="color:red;font-size:32rpx">*</text> -->设备SN</view>
					<view class="lineRight">
						<view class="">
							{{devicesnList.devicesn}}
						</view>
					</view>
				</view>
				<view class='line2'>
					<view class='lineLeft'><!-- <text style="color:red;font-size:32rpx">*</text> -->设备状态</view>
					<view class="lineRight">
						<view class="">
							{{filterB(devicesnList.devicestatus)}}
						</view>
					</view>
				</view>
				<view class='line2'>
					<view class='lineLeft'><!-- <text style="color:red;font-size:32rpx">*</text> -->是否在线</view>
					<view class="lineRight">
						<view class="">
							{{filterA(devicesnList.online_status)}}
						</view>
					</view>
				</view>
				<view class='line2'>
					<view class='lineLeft'><!-- <text style="color:red;font-size:32rpx">*</text> -->积分</view>
					<view class="lineRight">
						<view class="">
							{{devicesnList.mbpoit}}
						</view>
					</view>
				</view>
				<view class='line2'>
					<view class='lineLeft'><!-- <text style="color:red;font-size:32rpx">*</text> -->算力</view>
					<view class="lineRight">
						<view class="">
							{{devicesnList.calval}}
						</view>
					</view>
				</view>
				<view class='line2'>
					<view class='lineLeft'><!-- <text style="color:red;font-size:32rpx">*</text> -->份数</view>
					<view class="lineRight">
						<view class="">
							{{devicesnList.calmodel}}
						</view>
					</view>
				</view>
				<view class='line2' style="border-bottom: 1px solid #f5f5f5;">
					<view class='lineLeft'><!-- <text style="color:red;font-size:32rpx">*</text> -->购买时间</view>
					<view class="lineRight">
						<view class="">
							{{devicesnList.buytime}}
						</view>
					</view>
				</view>
				<!-- <view class='line'>
					<view class='lineLeft'>设备oweid</view>
					<view class="lineRight">
						<view class="">
							{{devicesnList.owerid}}
						</view>
					</view>
				</view>
				<view class='line'>
					<view class='lineLeft'>设备拥有者</view>
					<view class="lineRight">
						<view class="">
							{{devicesnList.Ower_name}}
						</view>
					</view>
				</view> -->
				
				<!-- <view class='line'>
					<view class='lineLeft'>推荐人id</view>
					<view class="lineRight">
						<view class="">
							唐
						</view>
					</view>
				</view> -->
				
				<!-- //可修改 -->
				<view class='line' style="margin-top:60rpx">
					<view class='lineLeft'><!-- <text style="color:red;font-size:32rpx">*</text> -->钱包地址</view>
					<view class="lineRight">

						<input class="input" @input="change"  v-model="devicesnList.wallet_address" placeholder-class="plaClass"
						 placeholder='请输入钱包地址'></input>
					</view>
				</view>
				<view class='line'>
					<view class='lineLeft'><!-- <text style="color:red;font-size:32rpx">*</text> -->推荐码</view>
					<view class="lineRight">
						<input class="input" @input="change"  v-model="devicesnList.referercode" placeholder-class="plaClass"
						 placeholder='请输入推荐码'></input>
					</view>
				</view>
				
				<view class='line'>
					<view class='lineLeft'><!-- <text style="color:red;font-size:32rpx">*</text> -->推荐人名字</view>
					<view class="lineRight">
						<input class="input" @input="change"  v-model="devicesnList.referername" placeholder-class="plaClass"
						 placeholder='请输入推荐人名字'></input>
					</view>
				</view>
				<view class='line'>
					<view class='lineLeft'><!-- <text style="color:red;font-size:32rpx">*</text> -->设备分组</view>
					<view class="lineRight">
						<!-- <input class="input" @input="change"  v-model="devicesnList.wallet_address" placeholder-class="plaClass"
						 placeholder='请输入详细住址'></input> -->
						 <picker v-if="fhStatus" @change="bindPickerChange" :value="index" :range="fhStatus" style="color:#000000;flex:1" class="myInput">
						 	<view class="" v-if="devicesnList.typeid">
						 		{{devicesnList.typename}}
						 	</view>
							<view v-else class="uni-input">{{fhStatus[index]}}</view>
						 </picker>
						 <view class="tips tri"></view>
					</view>
				</view>
				<view class='line' style="margin-bottom:100px;">
					<view class='lineLeft'><!-- <text style="color:red;font-size:32rpx">*</text> -->收益详情</view>
					<view class="lineRight">
						<view class="">
							
						</view>
						<view class="" style="height:100%;display:flex;align-items: center;width:80rpx;justify-content: flex-end;" @click="sc.goTo('/pages/index/income?devicesn='+devicesn)">
							<!-- <text class='iconfont icon-arrow-right' style="font-size:30rpx;color:#000000"></text> -->
							<image src="/static/you.png" mode="" style="height:30rpx;width:30rpx"></image>
						</view>
						<!-- <view class="tips tri"></view> -->
					</view>
				</view>
				<view class="buttonBox" @click="submit">
					提交
				</view>
			</view>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				form:{
					name:"tang"
				},
				flag:true,
				devicesn:"",
				devicesnList:'',
				fhStatus:[],
				index:0,
				flag:true,
				cltype:"",
				host:uni.getStorageSync("name")+":"+uni.getStorageSync('dk')+"/api"
			}
		},
		onLoad(e) {
			this.devicesn=e.devicesn
		},
		onShow(){
			this.fhStatus=[]
			this.loadData()
			this.loadData2()
		},
		methods: {
			valid(value){
				value   =   value.replace(/\s+/g,"");
				if (value=='') {
					return false;
				} else {
					return true;
				}
			},
			change(){
				
			},
			async submit(){
				if(!this.valid(this.devicesnList.wallet_address)){
					return this.sc.showMsg("钱包地址不能为空")
				}
				if(!this.valid(this.devicesnList.referercode)){
					return this.sc.showMsg("推荐码不能为空")
				}
				if(!this.valid(this.devicesnList.referername)){
					return this.sc.showMsg("推荐人名字不能为空")
				}
				if(this.index==0){
					return this.sc.showMsg("请选择设备组")
				}
				let result = await this.sc.callApi({
					data: {
						wallet_address:this.devicesnList.wallet_address,
						referercode:this.devicesnList.referercode,
						referername:this.devicesnList.referername,
						typeid:this.devicesnList.typeid?this.devicesnList.typeid:this.cltype[this.index - 1].id,
						devicesn:this.devicesn
					},
					url: "/devices/edit",
					method:"POST",
					host:this.host
				});
				if (result.success) {
					console.log(result.data)
					this.sc.showMsg("修改成功")
				}
				
			},
			bindPickerChange: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.flag=false
				this.index = e.target.value
				this.devicesnList.typeid=null
				console.log(this.cltype[this.index].policyid)
			},
			filterA(item) {
				if (item == 1) {
					return "在线"
				} else {
					return "离线"
				}
			},
			filterB(item) {
				if (item == 0) {
					return "未激活"
				} else if(item == 1){
					return "正常"
				} else if(item == 2){
					return "锁定"
				} else if(item == 3){
					return "作废"
				}
			},
			async loadData() {
				let result = await this.sc.callApi({
					data: {
						devicesn:this.devicesn
					},
					url: "/devices/edit"
				});
				if (result.success) {
					this.devicesnList = result.data;
					console.log(result.data)
				}
				
				// let result2 = await this.sc.callApi({
				// 	url: "/devices/drawpolicy"
				// });
				// if (result2.success) {
				// 	this.cltype = result2.data
				// 	this.cltype.forEach((item, index) => {
				// 		this.fhStatus.push(item.policy_name)
				// 	})
				// }
			},
			async loadData2() {
				let result = await this.sc.callApi({
					url: "/devices/type",
					host: this.host
				});
				this.fhStatus.push("分组选择")
				if (result.success) {
					console.log(result.data)
					this.cltype = result.data
			
					this.cltype.forEach((item, index) => {
						this.fhStatus.push(item.name)
					})
				}
			
			},
		}
	}
</script>

<style lang="scss">
	.head{
		display:flex;
		justify-content: space-between;
		padding:0 2%;
		line-height:40px;
		align-items: center;
		position:relative;
		background:#08b6f2;
		color:white
	}
	
	
	.shebeiContent{
		// margin:0 4%;
		padding:20rpx;
		
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
