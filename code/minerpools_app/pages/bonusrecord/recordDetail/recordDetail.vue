<template>
	<view>
		<view class="search">
			<!-- <view class="shebeiStatus" style="padding-left:20rpx;box-sizing: border-box;width:35%">
				<view style="font-size:30rpx;">分红结果:</view>
				<view class="jifen2">
					<picker @change="bindPickerChange" :value="index" :range="array" style="width:100rpx;margin-left:20rpx;font-size:28rpx;font-weight:bold;display:flex;height:70rpx;align-items: center">
						<view class="uni-input">{{array[index]}}</view>
					</picker>
				</view>
			</view>

			<view class="shebeiStatus" style="padding-left:20rpx;box-sizing: border-box;width:65%">
				<view style="font-size:30rpx;">设备SN:</view>
				<input type="text" value="" style="font-size:28rpx;font-weight:bold;margin-left:20rpx;border-bottom:1px solid #EEEEEE"/>
			</view> -->
			<!-- <view class="" style="display:flex;justify-content: space-around;width:100%;margin:0 auto;height:100rpx;align-items: center;border-bottom:1px solid #EEEEEE">
				<view class="" style="font-size:28rpx;font-weight:bold;">
					按组查询
				</view>
				<view   style="display:flex;;height:100rpx;align-items: center;">
					<view style="font-size:30rpx;">分红结果:</view>
					<view class="jifen2">
						<picker @change="bindPickerChange" :value="index" :range="array" style="width:100rpx;margin-left:20rpx;font-size:28rpx;font-weight:bold;display:flex;height:70rpx;align-items: center">
							<view class="uni-input">{{array[index]}}</view>
						</picker>
					</view>
				</view>
				<input type="text"  style="border:1px solid #ccc;border-radius:40rpx;width:500rpx;font-size:28rpx;height:60%;padding-left:30rpx" value="" placeholder="按设备查询"/>
			</view> -->
			<!-- <view class="shebeiStatus" style="padding-left:20rpx;box-sizing: border-box;width:100%">
				<view style="font-size:30rpx;">发放地址:</view>
				<input type="text" value="" style="font-size:28rpx;font-weight:bold;margin-left:20rpx;flex:1;margin-right:30rpx;border-bottom:1px solid #EEEEEE"/>
			</view> -->

			<!-- <view class="" style="margin-left:60rpx">
				<view class="jifen2">
					<picker v-if="fhStatus"  @change="bindPickerChange2" :value="index2" :range="fhStatus" style="width:140rpx;margin-left:20rpx;font-size:28rpx;font-weight:bold;display:flex;height:70rpx;align-items: center">
						<view class="uni-input">{{fhStatus[index2]}}</view>
					</picker>
				</view>
			</view>
			<view class="" style="margin-left:60rpx">
				<view class="jifen2">
					<picker v-if="fhStatus"  @change="bindPickerChange2" :value="index2" :range="fhStatus" style="width:140rpx;margin-left:20rpx;font-size:28rpx;font-weight:bold;display:flex;height:70rpx;align-items: center">
						<view class="uni-input">{{fhStatus[index2]}}</view>
					</picker>
				</view>
			</view> -->
			<view class="" style="display:flex;margin-top:20rpx;width:100%">
				<view class="" style="margin-left:20rpx">
					<view class="jifen2">
						<picker v-if="fhStatus" @change="bindPickerChange" :value="index" :range="fhStatus" style="display:flex;width:180rpx;font-size:28rpx;font-weight:bold;display:flex;height:70rpx;align-items: center">
							<view class="uni-input" style="display:flex;align-items: center;">
								<view class="">
									{{fhStatus[index]}}
								</view>
								<image src="/static/xia.png" mode="" style="width:32rpx;height:20rpx">
							</view>
						</picker>

					</view>
				</view>
				<view class="" v-if="fhStatus2.length>1" style="">
					<view class="jifen2">
						<picker @change="bindPickerChange2" :value="index2" :range="fhStatus2" style="font-size:28rpx;font-weight:bold;display:flex;height:70rpx;align-items: center">
							<view class="uni-input" style="display:flex;align-items: center;">
								<view class="">
									{{fhStatus2[index2]}}
								</view>
								<image src="/static/xia.png" mode="" style="width:32rpx;height:20rpx">
							</view>
						</picker>
					</view>
				</view>
				<view class="" style="display:flex;align-items: center;">
					<view class="" style="display:flex;justify-content: flex-end;">
						<view class="btn" @click="addList1">
							新增
						</view>
					</view>

					<view class="" style="display:flex;justify-content: flex-end;margin-right:60rpx;">
						
						<!-- <view class="btn" @click="export2">
							导出
						</view> -->
						<!-- <a class="table-btn" :href="exporturl" target='_blank'>下载入口</a> -->
						<view class="btn" v-if="myHtml" v-html="myHtml">
							
						</view>
					</view>
				</view>
			</view>
		</view>

		<view class="" style="margin-top: 50px;padding-bottom:100px;">
			<view v-if="addList" style="width:94%;margin:0 auto;padding-bottom:20rpx;border-bottom:1px solid #EEEEEE">
				<view class="content">
					<view class="line">
						<view class="myTitle" style="display:flex;align-items: center;">
							设备SN
						</view>
						<view class="myInput">
							{{addList.devicesn}}
						</view>
					</view>

					<view class="line">
						<view class="myTitle">
							分红数量
						</view>
						<input class="myInput" style="height:80%;border:1px solid #eeeeee;padding-left:10rpx;border-radius:40rpx" type="number"
						 placeholder="请编辑分红数量" @blur="addMyList($event)" />
					</view>
				</view>

				<view class="" style="display:flex;justify-content: flex-end;">
					<view class="btn" style="margin-right:20rpx" @click="save()">
						保存
					</view>
					<view class="btn" style="margin-right:20rpx" @click="deleteData1(index)">
						删除
					</view>
				</view>
			</view>
			<view v-for="(item,index) in pageListData" :key="index" style="width:94%;margin:0 auto;padding-bottom:20rpx;border-bottom:1px solid #EEEEEE"
			 :class="(index+1)==pageListData.length?'mar':''">
				<view class="content">
					<view class="line">
						<view class="myTitle" style="display:flex;align-items: center;">
							设备SN
						</view>
						<!-- <input class="myInput" type="text" v-model="total_num"/> -->
						<view class="myInput">
							{{item.devicesn}}
						</view>
					</view>
					<!-- <view class="line">
						<view class="myTitle" style="display:flex;align-items: center;">
							分红比率
						</view>
						<view class="myInput">
							{{item.draw_rate}}
						</view>
					</view> -->
					<view class="line">
						<view class="myTitle" style="display:flex;align-items: center;">
							执行状态
						</view>
						<!-- <input class="myInput" type="text" v-model="total_num"/> -->
						<view class="myInput">
							{{item.draw_status_label}}
						</view>
					</view>
					<view class="line" style="margin-bottom:10rpx">
						<view class="myTitle">
							分红数量
						</view>
						<input class="myInput" :disabled="item.draw_transFlag==2||item.draw_transFlag==3||item.draw_transFlag==5" :class="(item.draw_transFlag==2||item.draw_transFlag==3||item.draw_transFlag==5)?'':'bor'"
						 style="height:80%;" type="number" placeholder="请编辑分红数量" @blur="addUpdateList($event,item.detailid)" :value="item.draw_num" />
						<!-- <view class="myInput">
							123
						</view> -->
					</view>
					<!-- <view class="line">
						<view class="myTitle">
							发放状态
						</view>
						<view class="myInput">
							{{item.draw_status_label}}
						</view>
					</view> -->
					<!-- <view class="myItem myTitle columnFirst">
						分红名称
					</view>
					<view class="myItem columnSecond">
						分红很多
					</view>
		
					<view class="myItem myTitle columnFirst">
						分红类型
					</view>
					<view class="myItem columnSecond">
						按流量贡献比列
					</view>
					<view class="myItem myTitle columnFirst">
						开始时间
					</view>
					<view class="myItem columnSecond">
						2012-12-12
					</view>
					<view class="myItem myTitle columnFirst">
						结束时间
					</view>
					<view class="myItem columnSecond">
						2012-12-12
					</view>
					<view class="myItem myTitle columnFirst">
						分红总额
					</view>
					<view class="myItem columnSecond">
						123123
					</view>
					<view class="myItem myTitle columnFirst">
						设备总数
					</view>
					<view class="myItem columnSecond">
						240
					</view>
					<view class="myItem myTitle columnFirst">
						分红状态
					</view>
					<view class="myItem columnSecond">
						发放失败
					</view> -->
				</view>

				<view class="" style="display:flex;justify-content: flex-end;">
					<!-- <view class="btn" style="margin-right:20rpx" @click="save(item.detailid)">
						保存
					</view> -->
					<view class="btn" style="margin-right:20rpx" @click="deleteData1(item.detailid)">
						删除
					</view>
				</view>
			</view>
			<uni-load-more :status="pageLoadingStatus" class="xt">
				<view slot="nodatatmp" class="nodata" style="">
					<text>暂无数据</text>
				</view>
			</uni-load-more>

		</view>
		<!-- 遮罩层 -->
		<view class="" catchtouchmove="true" @click="isShow=!isShow" @touchmove.stop.prevent="moveHandle" v-show="isShow"
		 style="background: rgba(0,0,0,0.3);width:100%;height:100%;position:fixed;z-index:999;top:0">
		</view>
		<view class="" v-show="isShow" style="width:90%;margin-left:5%;z-index:1000;position:fixed;top:600rpx;background:white;height:360rpx;border-radius:40rpx;padding-top: 40px;">
			<!-- <view class="" style="display:flex;">
				<view class="">
					prikey:
				</view>
				
			</view> -->
			<textarea maxlength="-1" type="text" @blur="getPrikey($event)" value="" style="font-size:28rpx;width: 80%;padding:20rpx;margin: 0 auto;height: 200rpx;border: 1px solid #cccccc;border-radius: 10px;padding-left:20rpx;"
			 placeholder="请输入prikey并执行" />
			<view class="" style="display:flex;width:100%;justify-content: space-around;position:absolute;bottom:0;left:0">
				<view class="btn2" style="border-bottom-left-radius: 21px;border-right: 1px solid white;" @click="isShow=false">
					取消
				</view>
				<view class="btn2" style="border-bottom-right-radius: 21px;" @click="execution()">
					执行
				</view>
			</view>
		</view>
		<view class="" style="position: fixed;bottom: 100rpx;left: 0;right: 0;display:flex">
			<!-- <view class="buttonBox2" @click="submit">
				保存
			</view> -->
			<view class="buttonBox2" @click="isShow=true">
				执行
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				drawid: "",
				host: uni.getStorageSync("name") + ":" + uni.getStorageSync('dk') + "/api",
				data: "test",
				mydata: "",
				index: 0,
				fhStatus: [],
				index2: 0,
				cltype: "",
				dataList1: ["0"],
				fhStatus2: [],
				total_num: '',
				flag: false,
				cltype2: "",
				pageNum: 1, //查询页面
				pageSize: 5, //页面数量	
				pageListData: [],

				pageLoadingStatus: '',
				updateArr: [],
				addList: "",
				draw_num: "",
				isShow: false,
				prikey:"",
				exporturl:"",
				myHtml:''
			}
		},
		methods: {
			async export2(){
				let result=await this.sc.callApi({
					url:"/DeviceDrawDetail/export",
					data:{
						drawid:this.drawid
					},
					host:this.host
				})
				if(result.success){
					uni.downloadFile({
						url:result.data,
						success:(res)=>{
							if(res.statusCode==200){
								//保存到本地
								uni.saveFile({
									tempFilePath:res.tempFilePath,
									success:(re)=>{
										res.savedFilePath文件的保存路径
										//保存成功并打开文件
										uni.openDocument({
											filePath:re.savedFilePath,
											success:(res)=>console.log("成功打开文档")
										})
									},
									fail:()=>console.log('下载失败')
								})
								this.sc.showMsg("导出成功")
							}
						},
						fail:()=>console.log('下载失败')
					})
				}
				
			},
			getPrikey(e){
				this.prikey=e.detail.value
			},
			async execution(){
				let result=await this.sc.callApi({
					url:"/DeviceDrawDetail/docoin ",
					data:{
						drawid:this.drawid,
						prikey:this.prikey
					},
					host:this.host,
					method:"POST"
				})
				this.isShow=false
				if(result.success){
					
					this.sc.showMsg(result.msg)
					
				}
			},
			addMyList(e) {
				this.addList.draw_num = e.detail.value
			},
			async save() {

				if (!this.valid(this.addList.draw_num)) {
					return this.sc.showMsg("分红数量不能为空")
				}
				let result = await this.sc.callApi({
					url: "/DeviceDrawDetail/addcoinone ",
					data: this.addList,
					host: this.host,
					method: "POST"
				})
				if (result.success) {
					this.addList = ""
					this.sc.showMsg("添加成功")
					this.pageNum = 1
					this.loadPageData()
				}
			},
			unique(arr) {
				for (var i = 0; i < arr.length; i++) {
					for (var j = i + 1; j < arr.length; j++) {
						if (arr[i].detailid == arr[j].detailid) { //第一个等同于第二个，splice方法删除第一个
							arr.splice(i, 1);
							j--;
						}
					}
				}
				return arr;
			},
			async addUpdateList(e, detailid) {
				this.updateArr.push({
					detailid: detailid,
					draw_num: e.detail.value
				})
				this.unique(this.updateArr)
				// console.log(e.detail.value)
				// console.log(this.updateArr)
				if (!this.valid(e.detail.value)) {
					return this.sc.showMsg("分红数量不能为空")
				}
				let result = await this.sc.callApi({
					url: "/DeviceDrawDetail/savecoinone ",
					data: {
						drawid: this.drawid,
						detailid: detailid,
						draw_num: e.detail.value
					},
					host: this.host,
					method: "POST"
				})
				if (result.success) {
					this.sc.showMsg("修改成功")
				}
				if(result.code==0){
					console.log(111)
				}
			},
			// goInput(item){
			// 	this.updateArr.push
			// },
			async submit() {
				// if(!this.valid(this.draw_num)){
				// 	return this.sc.showMsg("分红数量不能为空")
				// }
				for (var i = 0; i < this.updateArr.length; i++) {
					this.updateArr[i].draw_num = this.updateArr[i].draw_num.replace(/\s+/g, "");
					if (this.updateArr[i].draw_num == '' || this.updateArr[i].draw_num == null || typeof(this.updateArr[i].draw_num) ==
						undefined) {
						return this.sc.showMsg("分红数量不能为空")
					}
				}
				console.log()
				let pdata = {
					drawid: this.drawid,
					details: this.updateArr
				}
				let result = await this.sc.callApi({
					url: "/DeviceDrawDetail/savecoinmore",
					data: pdata,
					host: this.host,
					method: "POST",
					contentType: 'application/json',
				})
				if (result.success) {
					console.log(result.date)
				}
			},
			valid(value) {
				value = value.replace(/\s+/g, "");
				if (value == '') {
					return false;
				} else {
					return true;
				}
			},
			async deleteData1(detailid) {
				let result = await this.sc.callApi({
					url: "/DeviceDrawDetail/deletecoin ",
					data: {
						detailid
					},
					host: this.host,
					method: "POST"
				})
				if (result.success) {
					this.sc.showMsg("删除成功")
					this.pageNum = 1
					this.loadPageData()
				}
			},
			addList1() {
				if (this.index == 0) {
					return this.sc.showMsg("请先选择设备组")
				}
				if (this.index2 == 0) {
					return this.sc.showMsg("请先选择设备")
				}
				if (this.addList) {
					return this.sc.showMsg("请先保存新增设备")
				}
				// this.addList.push({
				// 	{
				// 		detailid: "ooCi467SrLeV",
				// 		devicesn: "MCDV15JKR5JCX1I"
				// 		draw_num: "4.73080000"
				// 		draw_rate: "0.0385"
				// 	}
				// })
				this.addList = {
					drawid: this.drawid,
					devicesn: this.fhStatus2[this.index2],
					draw_num: ""
				}
			},
			bindPickerChange: function(e) {

				console.log('picker发送选择改变，携带值为', e.target.value)
				this.pageNum = 1
				this.index = e.target.value
				var that = this
				this.sc.callApi({
					data: {
						typeid: this.index ? this.cltype[this.index - 1].id : "",
						pageNum:0
					},
					url: "/DeviceDrawDetail/canadd",
					host: this.host
				}).then(res => {
					// /devices/all
					console.log(res)
					that.cltype2 = res.data.table.rows
					that.fhStatus2 = ["设备选择"]
					that.index2 = 0
					that.cltype2.forEach((item, index) => {
						that.fhStatus2.push(item.devicesn)
					})
					// if (that.index == 0) {
						
					// }
					
					console.log(that.fhStatus2)
				})
			},
			bindPickerChange2: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.index2 = e.target.value
				console.log(this.fhStatus2[this.index2])
			},
			async loadData2() {
				let result = await this.sc.callApi({
					url: "/devices/type",
					host: this.host
				});
				this.fhStatus.push("设备组选择")
				this.fhStatus2.push("设备选择")
				if (result.success) {
					console.log(result.data)
					this.cltype = result.data

					this.cltype.forEach((item, index) => {
						this.fhStatus.push(item.name)
					})
				}

			},
			// async loadData() {
			// 	let result = await this.sc.callApi({
			// 		data: {
			// 			drawid: this.drawid
			// 		},
			// 		url: "/DeviceDrawDetail/predevices",
			// 	});
			// 	if (result.success) {
			// 		this.dataList1=result.data
			// 	}

			// },
			async loadPageData() {
				this.pageLoadingStatus = 'loading';

				let result = await this.sc.callApi({
					data: {
						drawid: this.drawid,
						pageNum: this.pageNum,
						pageSize: this.pageSize
					},
					url: "/DeviceDrawDetail/predevices",
					host: this.host,
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
		onLoad(e) {
			this.drawid = e.drawid
			console.log(this.drawid)
			var that=this
			this.sc.callApi({
				url:"/DeviceDrawDetail/export",
				data:{
					drawid:this.drawid
				},
				host:this.host
			}).then(res=>{
				console.log(res)
				
				that.myHtml=` <a class="btn" href="${res.data}" style="text-decoration: none;color:#08b6f2" target='_blank'>导出</a>`
			})
		},
		onShow() {
			this.fhStatus = []
			this.loadData2()
			// this.loadData()
			this.loadPageData()
		},
		onReachBottom() {
			if (this.pageLoadingStatus === 'more') {
				this.pageNum = this.pageNum + 1;
				this.loadPageData();
			}
		},
	}
</script>

<style>
	.mar {
		padding-bottom: 100px
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
		width: 70%;
		margin-left: 50rpx;
		font-size: 28rpx;
		font-weight: bold;
		height: 80%;
		display: flex;
		align-items: center;
	}

	.myTitle {
		width: 200rpx;
		font-size: 30rpx;
		font-weight: bold
	}

	.search {
		/* height: 40px; */
		display: flex;
		align-items: center;

		flex-wrap: wrap;
		position: fixed;
		top: 40px;
		background: white;
		width: 100%;
		border-bottom: 1px solid #eeeeee;
		z-index: 1;
	}

	.shebeiStatus {
		display: flex;
		align-items: center;

	}

	.jifen2 {
		border-radius: 10rpx;
	}

	.btn {
		width: 120rpx;
		height: 40rpx;
		line-height: 40rpx;
		text-align: center;
		font-size: 24rpx;
		border-radius: 40rpx;
		border: 2rpx solid #08b6f2;
		margin-left: 40rpx;
		color: #08b6f2;
		
	}

	.content {
		/* display: flex;
		flex-wrap: wrap; */
		width: 100%;
		margin: 0 auto;
		margin-top: 30rpx
	}

	.myItem {
		width: 25%;
		font-size: 26rpx;
		margin-bottom: 6rpx
	}

	.myTitle {
		font-weight: bold;
	}

	.columnFirst {
		width: 20%
	}

	.columnSecond {
		width: 27%
	}

	.buttonBox2 {
		width: 81%;
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

	.btn2 {
		width: 50%;
		height: 41px;
		background: rgb(8, 182, 242);
		color: white;
		font-size: 14px;
		text-align: center;
		line-height: 41px;
	}
	.bor{
		padding-left:10rpx;
		border:1px solid #eeeeee;
		border-radius:40rpx
	}
</style>
