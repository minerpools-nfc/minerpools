<template>
	<view class="">
		<view class="line" style="margin-top:50rpx">
			<view class="">
				账号
			</view>
			<input class="myInput" type="text" v-model="userName" placeholder="请输入账号" />
		</view>
		<view class="line">
			<view class="">
				密码
			</view>
			<input class="myInput" type="password" v-model="userPwd" placeholder="请输入密码" />
		</view>
		<view class="line" style="margin-top:50rpx">
			<view class="">
				分组
			</view>
			<!-- <input class="myInput" type="text" v-model="user.userName" placeholder="请输入账号" /> -->
			<picker v-if="usertype" @change="bindPickerChange" :value="index" :range="array" style="" class="myInput">
				<view class="uni-input">{{usertype[index].title}}</view>
			</picker>
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
				userName:"",
				userPwd:"",
				array: [],
				usertype: "",
				index: 0,
				host:uni.getStorageSync("name")+":"+uni.getStorageSync('dk')+"/api"
			}
		},
		onLoad() {
			this.loadData()
		},
		methods: {
			bindPickerChange(){
				
			},
			async loadData() {
				let result2 = await this.sc.callApi({
				
					url: "/user/usertype",
					host:this.host
				});
				if (result2.success) {
					console.log(result2.data)
					this.usertype = result2.data
					this.usertype.forEach((item, index) => {
						this.array.push(item.title)
						if (item.id == this.myid) {
							this.index = index
						}
					})
				}
			},
			async submit() {
				if (this.userName==''||this.userPwd=='') {
					this.sc.showMsg("用户密码有误")
					return false;
				} else {
					let result = await this.sc.callApi({
						
						data: {
							username:this.userName,
							password:this.userPwd,
							typeid:this.usertype[this.index].id
						},
						url: "/user/add ",
						method:"POST"
					});
					if (result.success) {
						uni.showToast({
						    title: '添加成功',
						    duration: 1000,
							icon:"none",
							success:()=>{
								setTimeout(uni.navigateBack, 500)
							}
						});
					}
				}
				
			},
		}
	}
</script>

<style lang="scss">
	.line {
		font-size: 30rpx;
		font-weight: bold;
		display: flex;
		height: 80rpx;
		align-items: center;
		margin-left: 60rpx;
		margin-bottom: 40rpx;
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

	.myInput {
		border-bottom: 1px solid #EEEEEE;
		font-size: 26rpx;
		font-weight: 500;
		margin-left: 100rpx;
		width: 400rpx;
		height: 80rpx;
		line-height: 80rpx;
	}
</style>
