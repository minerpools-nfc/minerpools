<template>
	<view>
		<!-- <view class="jifen">
			<view class="line">
				<view class="myTitle" >
					接口地址
				</view>
				<input class="myInput" type="text" v-model.trim="name" placeholder="请输入接口地址" />
			</view>
			<view class="line">
				<view class="myTitle">
					端口
				</view>
				<input class="myInput" type="text" v-model.trim="dk" placeholder="请输入端口号" />
			</view>
			
			
		</view> -->
		<view class="" style="font-size:30rpx;margin:50rpx;color:#007AFF">
			接口地址：
		</view>
		<view class="jifen" style="margin-top:20rpx">
			<view class="line">
				<view style="height:60rpx;width: 30%;font-size:24rpx">
					<xfl-select  @change="change"
					:list="list"
					:clearable="false"
					:showItemNum="5" 
					:listShow="false"
					:isCanInput="false"  
					:placeholder = "'placeholder'"
					:initValue="myValue"
					:selectHideType="'independent'"
					>
					</xfl-select>
				</view>
				<input class="myInput" type="text" v-model.trim="name" placeholder="请输入接口地址" />
			</view>
			
			
		</view>
		
		<view class="buttonBox" @click="submit">
			保存
		</view>
	</view>
</template>

<script>
	import xflSelect from '@/components/xfl-select/xfl-select.vue';
	export default {
		data() {
			return {
				name:"",
				dk:"",
				// list: [
				// 	'苹果',
				// 	{value: '香蕉', disabled: true},
				// 	'葡萄',
				// 	'芒果',
				// 	'大白菜',
				// ],
				myValue:"",
				list: [
					'http://',
					'https://',
				],
			}
		},
		components: { xflSelect },
		methods: {
			visibleChange(isShow){ // 列表框的显示隐藏事件
				console.log('isShow::', isShow);
			},
			change({newVal, oldVal, index, orignItem}){ 
				// console.log(newVal, oldVal, index, orignItem);
				this.myValue=newVal
			},
			delete(str){
				
				str = str.replace(/\s*/g,''); // 去除字符串内所有的空格
				console.log(str)
			},
			submit(){
				this.sc.showMsg("设置成功")
				this.delete(this.name)
				this.delete(this.myValue)
				uni.setStorageSync("name", this.name)
				uni.setStorageSync("myValue", this.myValue)
				setTimeout(this.go,1000)
				
				
			},
			go(){
				uni.navigateBack({
					url: '/pages/login/login'
				})
			}
		},
		onShow(){
			if(uni.getStorageSync("myValue")){
				this.myValue=uni.getStorageSync("myValue")
			}else{
				this.myValue='http://'
			}
			if(uni.getStorageSync("name")){
				this.name=uni.getStorageSync("name")
			}
			if(uni.getStorageSync("dk")){
				this.dk=uni.getStorageSync("dk")
			}
		}
	}
</script>

<style>
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
		font-size: 26rpx;
		font-weight: 500;
		height: 70rpx;
		line-height: 70rpx;
		border-bottom: 1px solid #EEEEEE
	}

	.myTitle {
		width: 250rpx;
		font-size: 30rpx;
		font-weight: bold
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
		background-color: #817ef4;
		z-index: 999
	}
	
</style>
