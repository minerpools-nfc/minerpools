<template>
	<view>
		<view class=""  style="display:flex;width:86%;margin:0 auto;height:100rpx;align-items: center;border-bottom:1px solid #EEEEEE" >
			<view class="" style="width:200rpx;font-size:30rpx;font-weight:bold">
				设备组
			</view>
			<input type="text" v-model="value" style="flex:1;margin-left:60rpx;font-size:28rpx;" placeholder="请输入设备组名"/>
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
				value:""
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
				if(!this.valid(this.value)){
					return this.sc.showMsg("设备组不能为空")
				}
				let result=await this.sc.callApi({
					data:{
						name:this.value
					},
					url:"/DevicesType/add",
					host:this.host
				})
				if(result.success){
					this.sc.showMsg("添加成功")
					setTimeout(this.go,1000)
				}
			},
			go(){
				uni.navigateTo({
					url:"/pages/deviceGroup/deviceGroup"
				})
			}
		}
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
</style>
