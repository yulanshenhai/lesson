import {createRouter, createWebHashHistory} from 'vue-router'
import Welcome from '@/views/welcome'

// 所有组件的路由都在这个数组中配置
const routes = [
  {path: '/', name: 'Welcome', component: Welcome},
  {path: '/banner', name: 'Banner', component: import('@/views/welcome/banner')},
  {path: '/video-list', name: 'VideoList', component: import('@/views/welcome/video-list')},
  {path: '/common-header', name: 'CommonHeader', component: import('@/components/common-header')},
  {path: '/common-footer', name: 'CommonFooter', component: import('@/components/common-footer')},
  {path: '/free-video', name: 'FreeVideo', component: import('@/views/video-detail/free-video')},
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
