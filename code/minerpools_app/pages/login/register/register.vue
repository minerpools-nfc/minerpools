<template>
	<view>
		<view class="input-box">

			<view class="input-item">
				<view class="input-label">账号</view>
				<view class="input-body">
					<input placeholder="请输入账号"  v-model="loginName" maxlength="6" class="input">
				</view>
			</view>
			<view class="input-item">
				<view class="input-label">密码</view>
				<view class="input-body">
					<input v-model="password" type="text" :password="isHidePassword?true:false"
						style="margin-right: 50upx;" placeholder="请输入密码" maxlength="20" class="input" />
					<image @click="isHidePasswordClick" class="eye"
						:src="isHidePassword?'/static/img/attention.png':'/static/img/attention_forbid.png'"></image>
				</view>
			</view>
			<view class="input-item">
				<view class="input-label">确认密码</view>
				<view class="input-body">
					<input v-model="repassword" type="text" :password="isHidePassword?true:false"
						style="margin-right: 50upx;" placeholder="确认密码" maxlength="20" class="input" />
					<image @click="isHidePasswordClick" class="eye"
						:src="isHidePassword?'/static/img/attention.png':'/static/img/attention_forbid.png'"></image>
				</view>
			</view>
			<view class="input-item">
				<view class="input-label">姓名</view>
				<view class="input-body">
					<input placeholder="请输入姓名" type="" v-model="name"  class="input">
				</view>
			</view>
			<view class="input-item">
				<view class="input-label">手机</view>
				<view class="input-body">
					<input placeholder="请输入手机" type="number" v-model="phone"  class="input">
				</view>
			</view>
			<view class="input-item">
				<view class="input-label">邮箱</view>
				<view class="input-body">
					<input v-model="eMail" style="margin-right: 160upx;" placeholder="请输入邮箱" class="input">
					<button :disabled="!isCanSendCode" class="btn-code" @click="sendCode">{{sendMsg}}</button>
				</view>
			</view>
			<view class="input-item">
				<view class="input-label">验证码</view>
				<view class="input-body">
					<input placeholder="请输入验证码" type="number" v-model="code" maxlength="6" class="input">
				</view>
			</view>
		</view>
		<button class="button" @click="submit">注册</button>
	</view>
</template>

<script>
	let timer = '';
	import {
		checkPhone,
		checkPwd,
		checkCode,
		checkEmail
	} from "@/common/common.js"
	export default {
		data() {
			return {
				sendMsg: "发送验证码",
				isCanSendCode: true,
				isHidePassword: true,
				loginName: "",
				name:"",
				phone: "",
				password: "",
				repassword: "",
				code: "",
				eMail: ""
			};
		},
		onLoad() {},
		methods: {
			isHidePasswordClick() {
				this.isHidePassword = !this.isHidePassword
			},
			setCodeInterval() {
				//设置验证码重新发送定时器


				let time = 60;
				clearInterval(timer);

				timer = setInterval(() => {
					time--;
					this.isCanSendCode = false;
					this.sendMsg = time + "s"
					if (time <= 0) {
						this.isCanSendCode = true;
						this.sendMsg = "重新获取";
						clearInterval(timer);
					}
				}, 1000)
			},
			async getcode(){
				let result = await this.sc.callApi({
					data: {
						email: this.eMail,
					},
					url: "/BindUsers/validecode",
					host: uni.getStorageSync("myValue") + uni.getStorageSync("name") + "/api",
					method: "POST"
				})
				if(result.success){
					uni.showToast({
						icon: 'none',
						title: "发送成功!"
					})
				}
			},
			clearCodeInterval() {
				clearInterval(timer);
				this.codemsg = "获取验证码";
			},
			sendCode() {
				//发送验证码
				if (!checkEmail(this.eMail)) {
					return
				}
				this.getcode()
				this.setCodeInterval();
				/*
				 * 发送验证码逻辑
				 */
			},
			async submit() {
				if (this.loginName.replace(/\s*/g, "") == "") {
					return uni.showToast({
						icon: 'none',
						title: "用户名格式有误"
					})
				}
				if (!checkPwd(this.password)) {
					return;
				};
				if (this.password!=this.repassword) {
					return uni.showToast({
						icon: 'none',
						title: "两次输入密码不一致!"
					})
				};
				if (this.name.replace(/\s*/g, "") == "") {
					return uni.showToast({
						icon: 'none',
						title: "姓名不能为空"
					})
				}
				if (!checkPhone(this.phone)) {
					return;
				};
				if (!checkEmail(this.eMail)) {
					return
				}
				if (!checkCode(this.code)) {
					return;
				};
				/*
				 * 注册逻辑
				 */
				// this.$store.commit('update',['isLogin',true]);
				// uni.reLaunch({
				// 	url:'/pages/index/index'
				// })
				var that = this
				let result = await this.sc.callApi({
					data: {
						loginname: this.loginName,
						loginpwd: this.password,
						phone: this.phone,
						firstname:this.name,
						email: this.eMail,
						code: this.code
					},
					url: "/BindUsers/register",
					host: uni.getStorageSync("myValue") + uni.getStorageSync("name") + "/api",
					method: "POST"
				})
				if(result.success){
					uni.showToast({
						icon: 'none',
						title: "注册成功",
						duration: 1000,
						success: () => {
							setTimeout(that.go, 1000)
						}
					})
				}
			},
			go() {
				this.sc.goTo("/pages/login/login")
			},
		}
	}
</script>

<style>

</style>
