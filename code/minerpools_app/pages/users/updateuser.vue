<template>
	<view class="">
		<view class="line" style="margin-top:50rpx">
			<view class="">
				账号
			</view>
			<input class="myInput" type="text" v-model="user.userName" placeholder="请输入账号" />
		</view>
		<view class="line">
			<view class="">
				密码
			</view>
			<input class="myInput" type="password" v-model="user.userPwd" placeholder="请输入密码" />
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
				user: {
					userName: "",
					userPwd: ""
				},
				myid: "",
				oldpassword: "",
				array: [],
				index: 0,
				usertype: "",
				host:uni.getStorageSync("name")+":"+uni.getStorageSync('dk')+"/api"
			}
		},
		onLoad(e) {
			this.myid = e.myid
			
		},
		onShow(){
			this.loadData(this.myid)
		},
		methods: {
			bindPickerChange: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.index = e.target.value
			},
			async loadData(myid) {
				let result = await this.sc.callApi({
					data: {
						id: myid
					},
					url: "/user/edit",
					host:this.host
				});
				if (result.success) {
					this.user.userName = result.data.username;
					this.user.userPwd = result.data.password;
					this.oldpassword = result.data.password;
					console.log(result.data)
				}

				let result2 = await this.sc.callApi({

					url: "/user/usertype"
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
				let result = await this.sc.callApi({
					data: {
						id: this.myid,
						password: this.user.userPwd,
						oldpassword: this.oldpassword,
						username: this.user.userName,
						auth_group_id:this.usertype[this.index].id
					},
					url: "/user/edit",
					method:"POST"
				});
				if (result.success) {
					console.log(result.data)
					uni.showToast({
					    title: '修改成功',
					    duration: 1000,
						icon:"none",
						success:()=>{
							setTimeout(uni.navigateBack, 500)
						}
					});
					
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
