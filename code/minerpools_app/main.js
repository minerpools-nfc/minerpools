import Vue from 'vue'
import App from './App'
import sc from '@/common/sc.js'
import '@/common/font/iconfont.css'
import func  from '@/common/func'
import uniLoadMore from '@/components/uni-load-more/uni-load-more.vue';
import uniCollapse from '@/components/uni-collapse/uni-collapse.vue';
import uniCollapseItem from '@/components/uni-collapse-item/uni-collapse-item.vue';
import uniIcons from '@/components/uni-icons/uni-icons.vue'
Vue.config.productionTip = false

Vue.prototype.sc   = sc
Vue.prototype.func   = func
App.mpType = 'app'
Vue.use(uniLoadMore);
Vue.use(uniCollapse);
Vue.use(uniCollapseItem);
Vue.use(uniIcons);
const app = new Vue({
    ...App
})
app.$mount()
