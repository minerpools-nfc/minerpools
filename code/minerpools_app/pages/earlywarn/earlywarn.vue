<template>
	<view style="height:100%" @click="menuShow=false">
		<!-- 假设我需要状态栏到文字内容部分还有50px的距离 -->
		<view class="status_bar" :style="{height:height+50+'px'}">
			<view class="head">
				<view class="" style="width:40rpx;height:40rpx;display:flex;align-items: center;"  @click="sc.goTo('',5)">
					<image src="/static/zuo.png"  style="width:40rpx;height:40rpx"></image>
				</view>
				<view class="" @click="sc.goTo('/pages/login/login')">
					预警记录
				</view>
				<image src="/static/caidan.png" style="width:50rpx;height:50rpx" @click.stop="menuShow=!menuShow"></image>
				<!-- <view class="menu" v-if="menuShow">
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
				</view> -->
				
				<view v-if="menuShow" style="position:fixed;width:100px;right:0;top:50px;border-left:1px solid #eeeeee;z-index:1;color:black">
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
				</view>
			</view>
		</view>
		<view class="" :style="{marginTop:60+height+'px'}">
			<view class="wrap" v-for="(item,index) in pageListData" :key="index" >
				<view class="title">
					<view class="line">
						<view class="htitle" style="width:140rpx">
							设备SN：
						</view>
						<view class="htext">
							{{item.devicesn}}
						</view>
					</view>
					<view class="line" style="margin-right:0">
						<view class="htitle" style="width:100rpx">
							类型：
						</view>
						<view class="htext">
							{{filterA(item.alarm_type)}}
						</view>
					</view>
				</view>
			
				<view class="title">
					<view class="line">
						<view class="htitle">
							告警等级：
						</view>
						<view class="htext">
							{{filterB(item.servity)}}
						</view>
					</view>
					
				</view>
				<view class="title">
					<view class="line">
						<view class="htitle">
							首次发生时间：
						</view>
						<view class="htext">
							{{item.firsttime}}
						</view>
					</view>
					
				</view>
				<view class="title">
					
					<view class="line">
						<view class="htitle">
							最后发生时间：
						</view>
						<view class="htext">
							{{item.lasttime}}
						</view>
					</view>
				</view>
				<view class="title">
					<view class="line" style="margin-right:0">
						<view class="htitle">
							告警内容：
						</view>
						<view class="htext">
							{{item.alarm_msg}}
						</view>
					</view>
				</view>
			</view>
		</view>
		
		<uni-load-more :status="pageLoadingStatus" class="xt">
			<view slot="nodatatmp" class="nodata">
				<text>暂无数据</text>
			</view>
		</uni-load-more>
	</view>
</template>

<script >
	export default {
		data() {
			return {
				menuShow:false,
				pageNum: 1, //查询页面
				pageSize: 10, //页面数量	
				pageListData: [],
				pageLoadingStatus: '',
				host:uni.getStorageSync("name")+":"+uni.getStorageSync('dk')+"/api"
			}
		},
		methods: {
			go(url) {
				console.log(11)
				uni.navigateTo({
					url
			
				});
			},

			filterA(item) {
				if (item == 1) {
					return "发生告警"
				} else {
					return "恢复/删除告警"
				}
			},
			filterB(item) {
				if (item == 1) {
					return "恢复"
				} else if(item == 5){
					return "通知"
				} else if(item == 6){
					return "严重"
				} else if(item == 7){
					return "紧急"
				}
			},
			async loadPageData() {
				this.pageLoadingStatus = 'loading';
			
				let result = await this.sc.callApi({
					url: "/DeviceAlarm/index",
					host:this.host
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
		onShow(){
			this.loadPageData()
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
		onReachBottom() {
			if (this.pageLoadingStatus === 'more') {
				this.pageNum = this.pageNum + 1;
				this.loadPageData();
			}
		},
	}
</script>

<style lang="scss" scoped>
	.status_bar {
	        width: 100%;
	        background: #08b6f2;
	        position: relative;
			position: fixed;
			top: 0;
	    }
	    /* 调整状态栏标题的位置 */
	
	

	
	.head {
		bottom:0;
		left:0;
		right:0;
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

	.menu{
		position:absolute;
		right:0;
		top:40px;
		border:1px solid #C0C0C0;
		border-radius:4rpx;
		background:white;
		color:#999999
	}
	.menuList{
		margin:0 10rpx;
		height:60rpx;
		line-height:60rpx
	}
	.border_bottom {
		border-bottom: 1px solid #eeeeee;
	}
.wrap{
	width:96%;
	margin:0 auto;
	box-sizing: border-box;
	padding:20rpx;
	border-bottom:1px solid #EEEEEE;
	
	.title{
		display:flex;
		margin:10rpx 0;
		.line{
			display:flex;
			margin-right: 40px;
			align-items: center;
			.htitle{
				width:240rpx;
				font-size:30rpx;
				font-weight:bold
			}
			.htext{
				font-size:26rpx
			}
		}
	}
}
</style>
