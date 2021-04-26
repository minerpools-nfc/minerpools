<template>
	<view style="height:100%" @click="menuShow=false">
		<view class="" v-show="flag">
			<view class="jifen" style="border-bottom:1px solid #EEEEEE;padding-bottom:20rpx">
				<view class="line">
					<view class="myTitle" style="width:250rpx">
						分红名称
					</view>
					<input class="myInput" v-model="draw_name" type="text" placeholder="编辑分红名称" />
				</view>
				<!-- <view class="line">
					<view class="myTitle">
						策略描述
					</view>
					<textarea class="" style="height:70rpx;width:100%;margin-left: 110rpx;font-size: 26rpx;border-bottom:1px solid #eeeeee"
					 placeholder="编辑策略描述" />
					</view> -->
					<view class="line">
						<view class="myTitle" style="width:250rpx">
							金额
						</view>
						<input class="myInput" type="text" v-model="total_num" placeholder="编辑分红金额"/>
					</view>
					<view class="line" style="">
						<view class="myTitle">
							分红类型
						</view>
						<radio-group @change="change" :value="drawid_type" style="vertical-align: middle;">
							<label class="radio" style="margin-left: 20upx;font-size:28rpx;font-weight:bold;">
								<radio style="transform:scale(0.7)" value="1" checked="true"/>平均分配
							</label>
							<label class="radio" style="margin-left: 20upx;font-size:28rpx;font-weight:bold;">
								<radio style="transform:scale(0.7)" value="2" />按流量贡献比例
							</label>
						</radio-group>
					</view>
					<view class="line" style="" v-show="drawid_type==2">
						<view class="myTitle" >
							开始时间
						</view>
						<picker  mode="date" fields="day" :value="startime" @change="bindDateChange" style="flex:1;width:160rpx;margin-left:60rpx;font-size:28rpx;font-weight:bold;display:flex;height:70rpx;align-items: center">
							<view class="uni-input">{{startime}}</view>
						</picker>
					</view>
					<view class="line" style="" v-show="drawid_type==2">
						<view class="myTitle">
							结束时间
						</view>
						<picker  mode="date" fields="day" :value="endtime" @change="bindDateChange2" style="flex:1;width:160rpx;margin-left:60rpx;font-size:28rpx;font-weight:bold;display:flex;height:70rpx;align-items: center">
							<view class="uni-input">{{endtime}}</view>
						</picker>
					</view>
					<view class="line" >
						<view class="myTitle">设备组:</view>
						<picker v-if="fhStatus" @change="bindPickerChange2" :value="index2" :range="fhStatus" style="flex:1;width:160rpx;margin-left:60rpx;font-size:28rpx;font-weight:bold;display:flex;height:70rpx;align-items: center">
							<view class="uni-input">{{fhStatus[index2]}}</view>
						</picker>
					</view>
					<!-- <view class="line" style="">
						<view class="myTitle">
							启用状态
						</view>
						<radio-group  @change="change"  style="vertical-align: middle;font-size:28rpx;font-weight:bold;">
							<label class="radio" style="margin-left: 20upx;">
								<radio style="transform:scale(0.7)" value="1" />启用
							</label>
							<label class="radio" style="margin-left: 20upx;">
								<radio style="transform:scale(0.7)"  value="2" />不启用
							</label>
						</radio-group>
					</view> -->
					<!-- <view class="" style="display:flex;justify-content: flex-end;margin-right:60rpx">
						<view class="btn" @click="sc.goTo('/pages/bonus/updateBonus/updateBonus?policyid='+item.policyid)">
							修改
						</view>
						<view class="btn" @click="" @click="deleteCl(item.policyid)">
							删除配置
						</view>
					</view> -->
				</view>
				<!-- <view class="" style="display:flex;justify-content: flex-end;margin-right:60rpx;margin-top:20px">
					<view class="btn" @click="flag=false">
						添加分红设备
					</view>
				</view> -->
			    <view style="display:flex;flex-wrap: wrap;margin-top:40rpx">
					
			        <view v-if="item.checked==true" v-for="item in newItem" :key="item.value" style="display:flex;width:50%;padding:20rpx 0;justify-content:center;">
			        	<label class="uni-list-cell uni-list-cell-pd" style='display:flex;transform:scale(0.8);'   >
			        	    <view>{{item.name}}</view>
			        	</label>
			        	<view class="" @click="deleteA(item.value)" style="border: 1px solid red;border-radius:45rpx;color:red;font-size:24rpx;width:80rpx;display:flex;align-items: center;justify-content: center;margin-left:20rpx">
			        		删除
			        	</view>
			        </view>
			    </view>        
			<view class="buttonBox" @click="submit">
				保存配置
			</view>
		</view>
		
		
		<view class="" style="height:100%"  v-show="!flag">
			<view class="" style="display:flex;;width:100%;justify-content: space-around;margin:0 auto;height:100rpx;align-items: center;border-bottom:1px solid #EEEEEE">
				<view class="" style="font-size:28rpx;font-weight:bold;">
					按组查询
				</view>
				<input type="text" @focus="gfg" style="border:1px solid #ccc;border-radius:40rpx;width:300rpx;font-size:28rpx;height:60%;padding-left:30rpx" value="" placeholder="按设备查询"/>
			</view>
			<checkbox-group @change="checkboxChange" style="display:flex;flex-wrap: wrap;">
				
			    <view  v-for="item in items" :key="item.value" style="display:flex;width:50%;padding:10rpx 0">
			    	<label class="uni-list-cell uni-list-cell-pd" style='display:flex;transform:scale(0.8);width:100%'   >
			    	    <view>
			    	        <checkbox :value="item.value" :checked="item.checked" />
			    	    </view>
			    	    <view>{{item.name}}</view>
			    		
			    	</label>
			    </view>
			</checkbox-group>     
			<view class="" style="position: fixed;bottom: 100rpx;left: 0;right: 0;display:flex">
				<view class="buttonBox2" @click="all">
					全选/全不选
				</view>
				<view class="buttonBox2"  @click="save">
					保存
				</view>
			</view>
		</view>
		
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				flag:true,
				data: "积分不足",
				index: 0,
				menuShow: false,
				array: ['中国', '美国', '巴西', '日本'],
				// startime: this.func.getDate(),
				DeviceDrawPolicy:"",
				height2:"",
				host:uni.getStorageSync("name")+":"+uni.getStorageSync('dk')+"/api",
				items: [{
				                        value: 'USA',
				                        name: '美国'
				                    },
				                    {
				                        value: 'CHN',
				                        name: '中国',
				                        checked: 'true'
				                    },
				                    {
				                        value: 'BRA',
				                        name: '巴西'
				                    },
				                    {
				                        value: 'JPN',
				                        name: '日本'
				                    },
				                    {
				                        value: 'ENG',
				                        name: '英国'
				                    },
				                    {
				                        value: 'FRA',
				                        name: '法国'
				                    }
				                ],
				newItem:[],
				type:false,
				fhStatus:[],
				index2:0,
				cltype:"",
				
				drawid_type:1,
				draw_name:"",
				startime:this.func.getDate(),
				endtime:this.func.getDate(),
				total_num:"",
				devices_type:0
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
			async loadData2() {
				let result = await this.sc.callApi({
					url: "/devices/type/index",
					host:this.host
				});
				this.fhStatus.push("所有")
				if (result.success) {
					console.log(result.data)
					this.cltype = result.data
					
					this.cltype.forEach((item, index) => {
						this.fhStatus.push(item.name)
					})
				}
				
			},
			bindPickerChange2: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.pageNum=1
				this.index2 = e.target.value
				
			},
			
			all(){
				this.type=!this.type
				this.items.forEach((item, index) => {
					item.checked=this.type
				});
			},
			deleteA(value){
				this.newItem.map(item=>{
					if(item.value==value){
						item.checked=false
					}
				})
                this.items.forEach((item, index) => {
                    this.newItem.forEach(i => {
                        if (item.value == i.value) {
                            item.checked=i.checked
                        }
                    })
                });

			},
			save(){
				this.newItem = this.items.filter(i => {
				       return i.checked==true
				})
				this.flag=true
				console.log(this.newItem)
			},
			gfg(){
				console.log(222)
			},
			con(){
				console.log(111)
			},
			checkboxChange: function (e) {
			                var items = this.items,
			                    values = e.detail.value;
			                for (var i = 0, lenI = items.length; i < lenI; ++i) {
			                    const item = items[i]
			                    if(values.includes(item.value)){
			                        this.$set(item,'checked',true)
			                    }else{
			                        this.$set(item,'checked',false)
			                    }
			                }
							console.log(items)
			            },
			async deleteCl(policyid){
				let result=await this.sc.callApi({
					data:{
						policyid
					},
					url:"/DeviceDrawPolicy/delete",
					method:"POST",
					host:this.host
				})
				if(result.success){
					this.loadData()
					this.sc.showMsg("删除成功")
				}
			},
			bindDateChange: function(e) {
				this.startime = e.target.value
				// console.log(e)
			},
			bindDateChange2: function(e) {
				this.endtime = e.target.value
				// console.log(e)
			},
			change(e) {
				console.log(e)
				this.drawid_type=e.detail.value
			},
			go(url) {
				uni.navigateTo({
					url
			
				});
			},
			
			async submit(){
				console.log(this.fhStatus)
				if(!this.valid(this.draw_name)){
					return this.sc.showMsg("分红名称不能为空")
				}
				if(!this.valid(this.total_num)){
					return this.sc.showMsg("金额不能为空")
				}
				let result = await this.sc.callApi({
					data:{
						draw_name:this.draw_name,
						drawid_type:this.drawid_type,
						startime:this.startime,
						endtime:this.endtime,
						total_num:this.total_num,
						devices_type:this.index2?this.cltype[this.index2-1].id:"0"
					},
					method:"POST",
					url: "/DeviceDrawInfo/add",
					host:this.host
				});
				if(result.success){
					// console.log(result.data)
					this.sc.showMsg("新增成功")
					setTimeout(uni.navigateBack,500)
				}
			},
		},
		onShow(){
			this.height2=uni.getSystemInfoSync().screenHeight-200
			this.fhStatus=[]
			// this.loadData()
			this.loadData2()
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

<style scoped>
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
	.buttonBox2{
		width: 41%;
		margin: 0 auto;
		height: 84rpx;
		border-radius: 84rpx;
		color: white;
		display: flex;
		align-items: center;
		justify-content: center;
		/* position: fixed;
		bottom: 100rpx;
		left: 0;
		right: 0; */
		background-color: #08b6f2;
		z-index: 999
	}
	.btn {
		width: 170rpx;
		height: 40rpx;
		line-height: 40rpx;
		text-align: center;
		font-size: 24rpx;
		border-radius: 40rpx;
		border: 2rpx solid #08b6f2;
		margin-left: 40rpx;
		color:#08b6f2
	}
	.status_bar {
		width: 100%;
		background: #08b6f2;
		position: relative;
	}

	/* 调整状态栏标题的位置 */


	page {
		height: 100%;
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
		/* border-bottom: 1px solid #EEEEEE */
	}

	.myInput {
		width: 100%;
		margin-left: 110rpx;
		font-size: 28rpx;
		font-weight: bold;
	}

	.myTitle {
		width: 180rpx;
		font-size: 30rpx;
		font-weight: bold
	}
</style>
