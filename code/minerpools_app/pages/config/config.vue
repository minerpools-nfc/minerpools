<template>
	<view style="height:100%" @click="menuShow=false">
		<view class="status_bar" :style="{height:height+50+'px'}" style="z-index:1">
			<view class="head">
				<view class="" style="width:40rpx;height:40rpx;display:flex;align-items: center;" @click="sc.goTo('',5)">
					<image src="/static/zuo.png" style="width:40rpx;height:40rpx"></image>
				</view>
				<view class="" @click="sc.goTo('/pages/login/login')">
					接口配置
				</view>
				<image src="/static/caidan.png" style="width:50rpx;height:50rpx" @click.stop="menuShow=!menuShow"></image>
				<!-- <cover-view class="menu" v-if="menuShow" :style="menuShow?{zIndex:1}:{zIndex:-1}">
					<view class="menuList border_bottom" @click.stop="go('/pages/index/index')">
						设备列表
					</view>
					<view class="menuList border_bottom" @click.stop="go('/pages/config/config')">
						接口配置
					</view>
					<view class="menuList border_bottom" @click.stop="go('/pages/users/users')">
						用户管理
					</view>
					<view class="menuList border_bottom" @click.stop="go('/pages/earlywarn/allocation')">
						预警配置
					</view>
					<view class="menuList border_bottom" @click.stop="go('/pages/earlywarn/earlywarn')">
						预警记录
					</view>
					<view class="menuList border_bottom" @click.stop="go('/pages/bonus/bonus')">
						分红配置
					</view>
					<view class="menuList border_bottom" @click.stop="go('/pages/bonusrecord/bonusrecord')">
						分红记录
					</view>
					<view class="menuList" @click.stop="go('/pages/emailsetting/emailsetting')">
						邮箱配置
					</view>
				</cover-view> -->
				
				<cover-view v-if="menuShow" style="position:fixed;width:100px;right:0;top:50px;border-left:1px solid #eeeeee;z-index:1;color:black" :style="menuShow?{zIndex:1}:{zIndex:-1}">
					<uni-collapse ref="add" class="warp" @change="" style="width:100px;border-left:1px solid #eeeeee;">
						<uni-collapse-item    :show-animation="true"
						  title="设备">
							<view class="" @click.stop="go('/pages/index/index')" style="border-bottom:1px solid #cccccc;height: 40px;line-height: 40px;text-align: center;">
								设备列表
							</view>
							<view class="" @click.stop="go('/pages/deviceGroup/deviceGroup')" style="height: 40px;line-height: 40px;text-align: center;">
								设备组
							</view>
						</uni-collapse-item>
					</uni-collapse>
					
					<!-- 传show属性是否显示右边箭头 -->
					<uni-collapse ref="add"  class="warp" @change="go('/pages/bonusrecord/bonusrecord')" style="width:100px;border-left:1px solid #eeeeee">
						<uni-collapse-item    :show-animation="true" 
						  title="分红">
						</uni-collapse-item>
					</uni-collapse>
					<uni-collapse ref="add"  class="warp" @change="go('/pages/earlywarn/earlywarn')" style="width:100px;border-left:1px solid #eeeeee">
						<uni-collapse-item    :show-animation="true" 
						  title="预警">
						</uni-collapse-item>
					</uni-collapse>
					<uni-collapse ref="add" class="warp" @change="" style="width:100px;border-left:1px solid #eeeeee">
						<uni-collapse-item    :show-animation="true"
						  title="设置">
							<view class="" @click.stop="go('/pages/users/users')" style="border-bottom:1px solid #cccccc;height: 40px;line-height: 40px;text-align: center;">
								用户管理
							</view>
							<view class="" @click.stop="go('/pages/config/config')" style="border-bottom:1px solid #cccccc;height: 40px;line-height: 40px;text-align: center;">
								接口配置
							</view>
							<view class="" @click.stop="go('/pages/earlywarn/allocation')" style="border-bottom:1px solid #cccccc;height: 40px;line-height: 40px;text-align: center;">
								告警配置
							</view>
							<view class="" @click.stop="go('/pages/emailsetting/emailsetting')" style="height: 40px;line-height: 40px;text-align: center;">
								邮件发送
							</view>
						</uni-collapse-item>
					</uni-collapse>
				</cover-view>
			</view>
		</view>


		<view class="jifen" :style="{marginTop:60+height+'px'}">
			<view class="line">
				<view class="myTitle">
					连接账号
				</view>

			</view>
			<input class="myInput" type="text" v-model="dataObj.username" placeholder="请输入weline账号" :style="menuShow?{zIndex:-1}:{zIndex:1}" />
			<view class="line">
				<view class="myTitle">
					连接密码
				</view>

			</view>
			<input class="myInput" type="text" v-model="dataObj.userpwd" placeholder="请输入weline密码" />
			<view class="line">
				<view class="myTitle">
					设备查询url
				</view>
				<!-- <input class="myInput" type="text" v-model="dataObj.httpurl" placeholder="请输入设备获取url" /> -->
			</view>
			<!-- :class="menuShow?'cur':'cux'" -->
			<input class="myInput" type="text" style="font-size:20rpx;" :style="menuShow?{zIndex:-1}:{zIndex:1}" v-model="dataObj.httpurl"
			 placeholder="请输入设备获取url" />
			<view class="line">
				<view class="myTitle">
					流量查询url
				</view>

			</view>
			<input class="myInput" type="text" v-model="dataObj.nfcurl" placeholder="请输入收益获取url" />

			<view class="line">
				<view class="myTitle" style="width:100%">
					查询间隔(单位/min)
				</view>
			</view>
			<input class="myInput" type="number" v-model="dataObj.intaval" placeholder="请输入间隔时间" />
		</view>
		<!-- <json-excel class="export-excel-wrapper" :data="json_data" :fields="json_fields" name="filename.xls">
			上面可以自定义自己的样式，还可以引用其他组件button
			<el-button type="primary" size="small">导出EXCEL</el-button>
			<button type="default">导出</button>
		</json-excel> -->
		<view class="buttonBox" @click="submit">
			保存
		</view>
	</view>
</template>

<script>
	import JsonExcel from 'vue-json-excel'
	export default {
		data() {
			return {
				menuShow: false,
				height: null, //获取的状态栏高度
				dataObj: {
					httpurl: "",
					intaval: "",
					nfcurl: "",
					username: "",
					userpwd: ""
				},
				olduserpwd: "",
				host: uni.getStorageSync("name") + ":" + uni.getStorageSync('dk') + "/api",
				json_fields: {
					"Complete name": "name", //常规字段
					Telephone: "phone.mobile", //支持嵌套属性
					"Telephone 2": {
						field: "phone.landline",
						//自定义回调函数
						callback: value => {
							return `Landline Phone - ${value}`;
						}
					}
				},
				json_data: [{
						name: "Tony Peña",
						city: "New York",
						country: "United States",
						birthdate: "1978-03-15",
						phone: {
							mobile: "1-541-754-3010",
							landline: "(541) 754-3010"
						}
					},
					{
						name: "Thessaloniki",
						city: "Athens",
						country: "Greece",
						birthdate: "1987-11-23",
						phone: {
							mobile: "+1 855 275 5071",
							landline: "(2741) 2621-244"
						}
					}
				],
			}
		},
		components: {
			JsonExcel
		},
		methods: {
			valid(value) {
				value = value.replace(/\s+/g, "");
				if (value == '') {
					return false;
				} else {
					return true;
				}
			},
			async submit() {
				if (!this.valid(this.dataObj.httpurl)) {
					return this.sc.showMsg("设备获取url不能为空")
				}
				if (!this.valid(this.dataObj.nfcurl)) {
					return this.sc.showMsg("收益获取url不能为空")
				}
				if (!this.valid(this.dataObj.username)) {
					return this.sc.showMsg("weline账号不能为空")
				}
				if (!this.valid(this.dataObj.userpwd)) {
					return this.sc.showMsg("weline密码不能为空")
				}
				if (!this.valid(this.dataObj.intaval)) {
					return this.sc.showMsg("间隔时间不能为空")
				}
				let result = await this.sc.callApi({
					data: {
						httpurl: this.dataObj.httpurl,
						intaval: this.dataObj.intaval,
						nfcurl: this.dataObj.nfcurl,
						username: this.dataObj.username,
						userpwd: this.dataObj.userpwd,
						olduserpwd: this.olduserpwd
					},
					url: "/setting/interface",
					method: "POST",
					host: this.host
				});
				if (result.success) {
					this.sc.showMsg("保存成功")
				}
			},
			go(url) {
				console.log(11)
				uni.navigateTo({
					url

				});
			},
			async loadData() {
				let result = await this.sc.callApi({
					url: "/setting/interface",
					host: this.host
				});
				if (result.success) {
					this.dataObj = result.data
					this.olduserpwd = result.data.userpwd
				}

			},
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
		onShow() {
			this.loadData()
		}
	}
</script>

<style>
	.mar {
		padding-bottom: 100px
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

	.btn {
		width: 180rpx;
		height: 60rpx;
		line-height: 60rpx;
		text-align: center;
		font-size: 24rpx;
		border-radius: 40rpx;
		border: 2rpx solid #08b6f2;
		margin-left: 40rpx;
		color: #08b6f2
	}

	.status_bar {
		width: 100%;
		background: #08b6f2;
		position: relative;
		position: fixed;
		top: 0;
	}

	/* 调整状态栏标题的位置 */


	page {
		height: 100%
	}

	.head {
		bottom: 0;
		left: 0;
		right: 0;
		position: absolute;
		display: flex;
		justify-content: space-between;
		padding: 0 2%;
		line-height: 40px;
		align-items: center;
		/* position: relative; */
		background: #08b6f2;
		color: white
	}

	.menu {
		position: absolute;
		right: 0;
		top: 40px;
		border: 1px solid #C0C0C0;
		border-radius: 4rpx;
		background: white;
		color: #999999
	}

	.menuList {
		margin: 0 10rpx;
		height: 60rpx;
		line-height: 60rpx
	}

	.border_bottom {
		border-bottom: 1px solid #C0C0C0;
	}

	.line {
		display: flex;
		width: 86%;
		margin: 0 auto;
		height: 70rpx;
		align-items: center;
		margin-top: 50rpx
			/* border-bottom: 1px solid #EEEEEE */
	}

	.myInput {
		width: 86%;
		margin-left: 110rpx;
		font-size: 26rpx;
		font-weight: 500;
		height: 70rpx;
		line-height: 70rpx;
		border-bottom: 1px solid #EEEEEE;
		margin: 0 auto;
		font-size: 20rpx
	}

	.myTitle {
		width: 250rpx;
		font-size: 30rpx;
		font-weight: bold
	}
</style>
