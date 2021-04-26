<style lang="scss" scoped>
</style>
<template>
	<!-- <view>
        <view class="input-box">
            <view class="input-item">
                <view class="input-label">账号</view>
				<view class="input-body">
					<input v-model="username"  placeholder="请输入账号" class="input" >
				</view>
            </view>
            <view class="input-item">
				<view class="input-label">密码</view>
				<view class="input-body">
					<input v-model="password" type="text" :password="isHidePassword?true:false" style="margin-right: 50upx;" placeholder="请输入密码" maxlength="20"  class="input" />
					<image @click="isHidePasswordClick" class="eye" :src="isHidePassword?'/static/img/attention.png':'/static/img/attention_forbid.png'"></image>
				</view>
            </view>
        </view>
        <button class="button" @click="submit">登录</button>
		
		<view  style="position:fixed;bottom:200rpx;right:80rpx;display:flex;align-items: center;" @click="sc.goTo('./config/config')">
			<image src="/static/peizhi.png" style="width:40rpx;height:40rpx"></image>
			<text style="color:#8a8a8a">配置</text>
		</view>
    </view> -->
	<view class="" style="height:100%" @click="isShow=false">
		<view
			style="background-image: url(../../static/loginback.png);background-size:cover;width:100%;background-position-x: 50%;"
			:style="{paddingTop:height*2+20+'rpx',height:400-height+'rpx'}">
			<view class="" style="display:flex;justify-content: flex-end;" @click="sc.goTo('./config/config')">
				<image src="/static/menu2.png" mode=""
					style="width:40rpx;height:40rpx;margin-right:20rpx;padding:10rpx"></image>
			</view>
			<view class="" style="display:flex;justify-content: center;">
				<image src="/static/lologo.png" mode="" style="width:296rpx;height:58rpx;margin-top:110rpx"></image>
			</view>
			<!-- <view class="" style="display:flex;justify-content: center;">
				<image src="/static/NetFlowCoin.png" mode="" style="width:180rpx;height:24rpx;margin-top:18rpx"></image>
			</view> -->
		</view>

		<view class="" style="margin-left:50rpx;font-size:70rpx;margin-top:50rpx;">
			登录
		</view>
		<view class="" style="font-size:26rpx;margin:34rpx 0;margin-left:50rpx;color:#c2c2c2">
			请输入您的用户账号
		</view>
		<view
			style="text-align: left;border-radius:12rpx;font-size:24rpx;margin:34rpx 50rpx;background:#f3f2f2;width:auto;box-sizing: border-box;height:100rpx;">
			<xfl-select :list="list" :clearable="false" :showItemNum="5" :listShow="isShow" :isCanInput="true"
				:style_Container="listBoxStyle" :placeholder="'账号'" :initValue="list[0]" :selectHideType="'independent'"
				:myFontA="myFontA" @blur="getValue" @change="change" @visible-change="visibleChange">
			</xfl-select>
		</view>
		<!-- <input type="text" value="" v-model.trim="username" placeholder="账号" style=";border-radius:12rpx;font-size:24rpx;margin:34rpx 50rpx;background:#f3f2f2;width:auto;box-sizing: border-box;height:100rpx;padding-left:50rpx"/> -->
		<input type="password" value="" class='uni-input' v-model.trim="password" placeholder="密码"
			style=";border-radius:12rpx;font-size:24rpx;margin:34rpx 50rpx;background:#f3f2f2;width:auto;box-sizing: border-box;height:100rpx;padding-left:50rpx" />
		<!-- <view class="" style="display:flex;font-size:24rpx;margin:34rpx 0;margin-left:50rpx;color:#a6a4a4;justify-content: flex-end;margin:34rpx 50rpx;width:auto;">
			<text>忘记密码?</text>
		</view> -->
		<checkbox-group name="" @change="checkboxChange" style="margin-left:80rpx;font-size:28rpx">
			<label>
				<checkbox value="cb" :checked="isChecked" style="transform:scale(0.7)" />记住密码
			</label>
		</checkbox-group>
		<button class=""
			style="font-size:26rpx;margin:34rpx 0;margin-left:50rpx;color:white;margin:34rpx 50rpx;margin-top:100rpx;width:auto;background:#817ef4;height:100rpx;line-height:100rpx;border-radius:50rpx"
			@click="submit">立即登录</button>

		<!-- <view class="" style="text-align: center;color:#c2c2c2;font-size:30rpx">
			<text @click="register">还没有账号? 点此注册.</text>
		</view> -->
	</view>
	<!-- <view class="status_bar" :style="{height:height*2+50+'rpx'}" style="z-index:1">
		<view class="head">
			<view class="" style="width:40rpx;height:40rpx;display:flex;align-items: center;" @click="sc.goTo('',5)">
				<image src="/static/zuo.png" style="width:40rpx;height:40rpx"></image>
			</view>
			<view class="" @click="sc.goTo('/pages/login/login')">
				邮箱配置
			</view>
			<image src="/static/caidan.png" style="width:50rpx;height:50rpx" @click.stop="menuShow=!menuShow"></image>
			
			
			
		</view>
	</view> -->
</template>
<script>
	import xflSelect from '@/components/xfl-select/xfl-select.vue';
	import cmdProgress from "@/components/cmd-progress/cmd-progress.vue"
	import {
		checkPhone,
		checkPwd
	} from "@/common/common.js"
	export default {
		data() {
			return {
				myValue: "",
				listBoxStyle: `height:100rpx; font-size: 24rpx;background: rgb(243, 242, 242);padding-left:50rpx;border:none`,
				myFontA: `font-size:24rpx`,
				list: [


				],
				isHidePassword: true,
				username: "",
				password: "",
				isShow: false,
				isChecked: false,
				list2:[]
			};
		},
		components: {
			cmdProgress,
			xflSelect
		},
		onShow() {
			this.list=[]
			if (uni.getStorageSync('list')) {
				var list=uni.getStorageSync('list')
				this.list2=uni.getStorageSync('list')
				this.list=uni.getStorageSync('list2')
				for(var i=0;i<list.length;i++){
					// this.list.push(list[i].username)
					if(list[i].username==this.list[0]){
						this.password=list[i].password
						this.isChecked=list[i].isChecked
					}
				}
				// this.list = uni.getStorageSync('list')
				this.username = this.list[0]
				// this.password=list[0].password
				// this.isChecked=list[0].isChecked
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
		},
		methods: {
			unique(arr) {
				for (var i = 0; i < arr.length; i++) {
					for (var j = i + 1; j < arr.length; j++) {
						if (arr[i].username == arr[j].username) { //第一个等同于第二个，splice方法删除第二个
							arr.splice(j, 1);
							j--;
						}
					}
				}
				return arr;
			},
			checkboxChange(e) {
				console.log(e)
				if (e.detail.value[0] == "cb") {
					this.isChecked = true
				} else {
					this.isChecked = false
				}
			},
			getValue(e) {
				console.log(e.detail.value)
				this.username = e.detail.value
				this.username = this.username.replace(/\s*/g, "");
				// console.log(this.username)
			},
			visibleChange(isShow) { // 列表框的显示隐藏事件
				// console.log('isShow::', isShow);
				this.isShow = isShow
			},
			change({
				newVal,
				oldVal,
				index,
				orignItem
			}) {
				// console.log(newVal, oldVal, index, orignItem);
				this.username = newVal
				if (uni.getStorageSync('list')) {
					var list=uni.getStorageSync('list')
					for(var i=0;i<list.length;i++){
						// this.list.push(list[i].username)
						if(list[i].username==newVal){
							this.password=list[i].password
							this.isChecked=list[i].isChecked
						}
					}
					// this.password=list[0].password
					console.log(this.password)
				}
			},
			goConfig() {
				this.sc.goTo("/pages/login/config/config")
			},
			register(){
				if(uni.getStorageSync("name")){
					this.sc.goTo("/pages/login/register/register")
				}else{
					this.sc.showMsg("请先配置接口参数")
				}	
			},
			async submit() {
				this.username = this.username.replace(/\s*/g, "");
				var that = this
				if (uni.getStorageSync("name")) {
					let result = await this.sc.callApi({
						data: {
							username: this.username,
							password: this.password,
						},
						url: "/BindUsers/login ",
						host: uni.getStorageSync("myValue") + uni.getStorageSync("name") + "/api",
						method: "POST"
					})

					if (result.success) {
						this.list.unshift(this.username)
						this.list=[...new Set(this.list)] 
						uni.setStorageSync('list2', this.list)
						var obj={
							username:this.username,
							password:this.isChecked==true?this.password:"",
							isChecked:this.isChecked
						}
						
						this.list2.unshift(obj)
						this.unique(this.list2)
						
						console.log(this.list2)
						uni.setStorageSync('list', this.list2)
						uni.setStorageSync("mtoken", result.data.token)
						uni.setStorageSync("user", this.username)
						uni.showToast({
							title: '登录成功',
							duration: 1000,
							icon: "none",
							success: () => {
								setTimeout(that.go, 1000)
							}
						});
					}
				} else {
					this.sc.showMsg("请先配置接口参数")
					setTimeout(that.goConfig, 1000)

				}






			},
			go() {
				uni.switchTab({
					url: "/pages/index/index"
				})
			}
		}
	}
</script>
<style lang="scss">
	/*每个页面公共css */
	page {
		height: 100%
	}

	.input-box {
		padding: 50upx;
		font-size: 30upx;

		.input-item {
			display: flex;
			background: white;
			border-bottom: 1upx solid #eeeeee;
			line-height: 100upx;
			height: 100upx;

			.input-label {
				width: 150upx;
			}

			.input-body {
				position: relative;
				height: 100upx;
				width: calc(100% - 150upx);

				.input {
					line-height: 100upx;
					height: 100upx;
					position: relative;
					font-size: 28upx;
				}

				.eye {
					position: absolute;
					height: 50upx;
					width: 50upx;
					right: 0;
					top: 50%;
					transform: translateY(-50%);
				}

				.btn-code {
					position: absolute;
					right: 0upx;
					top: 50%;
					transform: translateY(-50%);
					background: none;
					color: #205592;
					width: 160upx;
					font-size: 24upx;
					box-sizing: border-box;
					text-align: right;
					padding: 0;
					height: 100upx;
					line-height: 100upx;
				}
			}

		}

		.select {
			padding-top: 40upx;
			display: flex;
			justify-content: space-between;
			color: #003B67;
		}
	}

	.button {
		margin: 0 30upx;
		background: #08B6F2;
		border-radius: 50upx;
		line-height: 80upx;
		height: 80upx;
		color: white;
		font-size: 32upx;
	}
</style>
