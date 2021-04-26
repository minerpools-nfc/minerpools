<template>
	<view style="height:100%" @click="menuShow=false">
		<!-- 假设我需要状态栏到文字内容部分还有50px的距离 -->
		<view class="status_bar" :style="{height:height+50+'px'}">
			<view class="head">
				<view class="" style="width:40rpx;height:40rpx;display:flex;align-items: center;" @click="sc.goTo('',5)">
					<image src="/static/zuo.png" style="width:40rpx;height:40rpx"></image>
				</view>
				<view class="" @click="sc.goTo('/pages/login/login')">
					预警配置
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
							<view class="" @click.stop="go('/pages/earlywarn/earlywarn')" style="border-bottom:1px solid #cccccc;height: 40px;line-height: 40px;text-align: center;">
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


		<!-- <view class="title">
			<view class='tab' :class='[currentData == 1 ? "tabBorer" : ""]' data-current="0" @click='changeCurrent(1)'>
				积分
			</view>
			<view class='tab' :class='[currentData == 2 ? "tabBorer" : ""]' data-current="1" @click='changeCurrent(2)'>
				设备状态
			</view>
			<view class='tab' :class='[currentData == 3 ? "tabBorer" : ""]' data-current="2" @click='changeCurrent(3)'>
				接口状态
			</view>
		</view> -->

		<view class="content" :style="{marginTop:60+height+'px'}">
			<view class="jifen"  >
				<view class="" style="display:flex;width:86%;margin:0 auto;height:100rpx;align-items: center;border-bottom:1px solid #EEEEEE">
					<view class="" style="width:200rpx;box-sizing: border-box;display:flex;justify-content: space-between;">
						<text style="font-size:30rpx;font-weight:bold">积分</text>
						<text style="font-size:30rpx;font-weight:bold">≤</text>
					</view>
					<input type="text" v-model="cfg1.mbpoint" style="margin-left:60rpx;font-size:28rpx;font-weight:bold" />
				</view>
				
				<view class="" style="display:flex;width:86%;margin:0 auto;height:100rpx;align-items: center;border-bottom:1px solid #EEEEEE">
					<view class="" style="width:200rpx;font-size:30rpx;font-weight:bold">
						设备状态预警
					</view>
					<radio-group @change="change1" :value="cfg1.onlinestatus" style="vertical-align: middle;">
						<label class="radio" style="margin-left: 20upx;">
							<radio style="transform:scale(0.7)" value="1" :checked="cfg1.onlinestatus==1?true:false" />启用
						</label>
						<label class="radio" style="margin-left: 20upx;">
							<radio style="transform:scale(0.7)" value="2" :checked="cfg1.onlinestatus==2?true:false"/>不启用
						</label>
					</radio-group>
				</view>
				<view class="" style="display:flex;width:86%;margin:0 auto;height:100rpx;align-items: center;border-bottom:1px solid #EEEEEE">
					<view class="" style="width:200rpx;font-size:30rpx;font-weight:bold">
						连接状态预警
					</view>
					<radio-group @change="change2" :value="cfg1.interfacestatus" style="vertical-align: middle;">
						<label class="radio" style="margin-left: 20upx;">
							<radio style="transform:scale(0.7)" value="1" :checked="cfg1.interfacestatus==1?true:false" />启用
						</label>
						<label class="radio" style="margin-left: 20upx;">
							<radio style="transform:scale(0.7)" value="2" :checked="cfg1.interfacestatus==2?true:false"/>不启用
						</label>
					</radio-group>
				</view>
				
				<view class="" style="display:flex;justify-content: flex-end;margin-right:60rpx;margin-top:40rpx">
					<view class="btn" @click="addList1">
						新增邮箱用户
					</view>
				</view>
				<view class=""  style="display:flex;width:86%;margin:0 auto;height:100rpx;align-items: center;border-bottom:1px solid #EEEEEE" v-for="(data,cd) in dataList1" :key="cd" :class="(cd+1)==dataList1.length?'mar':''">
					<view class="" style="width:200rpx;font-size:30rpx;font-weight:bold">
						邮箱用户{{cd+1}}
					</view>
					<input type="text" :value="data" @input="changeEmail($event,cd)" style="flex:1;margin-left:60rpx;font-size:28rpx;" placeholder="请输入邮箱用户"/>
					<view class="" @click="deleteData1(cd)" style="margin-left:30rpx;border:1px solid #08b6f2;border-radius:50rpx;font-size:26rpx;color:#08b6f2;width:100rpx;height:50rpx;line-height:50rpx;text-align: center;">
						删除
					</view>
				</view>
			</view>
			
			
		</view>
		<view class="buttonBox" @click="submit">
			提交
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				current:1,
				onOrOff:1,
				num:10,
				menuShow: false,
				currentData: 1,
				index: 0,
				index2: 0,
				index3: 0,
				data:'',
				array: ['恢复', '通知', '严重', '紧急'],
				dataList1:[],
				dataList2:[],
				dataList3:[],
				cfg1:"",
				cfg2:"",
				cfg3:"",
				host:uni.getStorageSync("name")+":"+uni.getStorageSync('dk')+"/api"
				
			}
		},
		methods: {
			changeEmail(e,cd){
				this.dataList1[cd]=e.detail.value
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
				if(!this.valid(this.cfg1.mbpoint)){
					return this.sc.showMsg("积分不能为空")
				}
				// if(!this.valid(this.cfg1.cfg_msg)){
				// 	return this.sc.showMsg("积分预警内容不能为空")
				// }
				// if(!this.valid(this.cfg2.cfg_msg)){
				// 	return this.sc.showMsg("设备状态预警内容不能为空")
				// }
				// if(!this.valid(this.cfg3.cfg_msg)){
				// 	return this.sc.showMsg("接口状态预警内容不能为空")
				// }
				
				this.cfg1.servity=this.index+1
				// this.cfg2.servity=this.index2+1
				// this.cfg3.servity=this.index3+1
				this.getEmail(this.dataList1,this.cfg1)
				// this.getEmail(this.dataList2,this.cfg2)
				// this.getEmail(this.dataList3,this.cfg3)
				// var arr=[this.cfg1,this.cfg2,this.cfg3]
				console.log(this.dataList1)
				// console.log(arr)
				let result=await this.sc.callApi({
					data: {
						mbpoint:this.cfg1.mbpoint,
						onlinestatus:this.cfg1.onlinestatus,
						interfacestatus:this.cfg1.interfacestatus,
						email:this.cfg1.email
					},
					url: "/setting/alarm",
					method: 'POST',
					host:this.host
				})
				if(result.success){
					this.sc.showMsg("提交成功")
					this.loadData()
				}
			},
			getEmail(data,obj){
				console.log(data)
				for(var i = 0;i<data.length;i++){
					data[i] =data[i].replace(/\s+/g,"");
				    if(data[i]==''||data[i]==null||typeof(data[i])==undefined){
				        data.splice(i,1);
				        i=i-1;
				    }
				}
				console.log(data)
				if(data.length==0){
					obj.email=""
				}else if(data.length==1){
					obj.email=data[0]
				}else{
					obj.email=data.join()
				}
			},
				
			// first
			addList1() {
				this.dataList1.push("")
			},
			deleteData1(num) {
				this.dataList1.splice(num,1)
			},
			change1(e){
				this.cfg1.onlinestatus=e.detail.value
			},
			change2(e){
				this.cfg1.interfacestatus=e.detail.value
			},
			bindPickerChange1: function(e) {
				this.index = e.target.value
			},
			changeEmail1(e,cd){
				this.dataList1[cd]=e.detail.value
			},
			// // second
			// addList2() {
			// 	this.dataList2.push("")
			// },
			// deleteData2(num) {
				
			// 	this.dataList2.splice(num,1)
			// },
			// change2(e){
			// 	this.cfg2.enable=e.detail.value
			// },
			// bindPickerChange2: function(e) {
			// 	this.index2 = e.target.value
			// },
			// changeEmail2(e,cd){
			// 	this.dataList2[cd]=e.detail.value
			// },
			// mychange2(e){
			// 	this.cfg2.threshold=e.detail.value
			// },
			// // third
			// addList3() {
			// 	this.dataList3.push("")
			// },
			// deleteData3(num) {
				
			// 	this.dataList3.splice(num,1)
			// },
			// change3(e){
			// 	this.cfg3.enable=e.detail.value
			// },
			// bindPickerChange3: function(e) {
			// 	this.index3 = e.target.value
			// },
			// changeEmail3(e,cd){
			// 	this.dataList3[cd]=e.detail.value
			// },
			// mychange3(e){
			// 	this.cfg3.threshold=e.detail.value
			// },
			
			changeCurrent: function(type) {
				this.currentData = type
				this.current = type
			},
			go(url) {
				console.log(11)
				uni.navigateTo({
					url
			
				});
			},
			async loadData() {
				let result = await this.sc.callApi({
					url: "/setting/alarm",
					host:this.host
				});
				if (result.success) {
					// console.log(result.data)
					// var arr=result.data
					// arr.forEach(item=>{
					// 	if(item.cfgid==1){
					// 		this.cfg1=item
					// 	}
					// 	if(item.cfgid==2){
					// 		this.cfg2=item
					// 	}
					// 	if(item.cfgid==3){
					// 		this.cfg3=item
					// 	}
					// })
					this.cfg1=result.data
					if(this.cfg1.email.length>0){
						this.dataList1=this.cfg1.email.split(",")
						this.index=this.cfg1.servity-1
					}
					
					console.log(this.dataList1)
					// if(this.cfg2.email.length>0){
					// 	this.dataList2=this.cfg2.email.split(",")
					// 	this.index2=this.cfg2.servity-1
					// }
					// if(this.cfg3.email.length>0){
					// 	this.dataList3=this.cfg3.email.split(",")
					// 	this.index3=this.cfg3.servity-1
					// }
				}
				
			},
		},
		onShow(){
			this.loadData()
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
		}
	}
</script>

<style lang="scss" scoped>
	.mar{
		padding-bottom:100px
	}
	.status_bar {
		width: 100%;
		background: #08b6f2;
		position: relative;
		position: fixed;
		top: 0;
	}

	/* 调整状态栏标题的位置 */
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

	.title {
		display: flex;
		justify-content: space-around;
		font-size: 30rpx;
		padding-bottom: 20rpx;
		margin-top: 40rpx;
		font-weight: bold
	}

	.tab {
		padding-bottom: 10rpx
	}

	.tabBorer {
		border-bottom: 2px solid #08b6f2;
		color: #08b6f2
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
</style>
