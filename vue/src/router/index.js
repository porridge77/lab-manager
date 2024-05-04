import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect: '/home',  // 重定向到主页
    children: [
      { path: '403', name: 'NoAuth', meta: { name: '无权限' }, component: () => import('../views/manager/403') },
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/manager/Home') },
      { path: 'admin', name: 'Admin', meta: { name: '管理员信息' }, component: () => import('../views/manager/Admin') },
      { path: 'password', name: 'Password', meta: { name: '修改密码' }, component: () => import('../views/manager/Password') },
      { path: 'labadmin', name: 'LabAdmin', meta: { name: '实验室管理员' }, component: () => import('../views/manager/LabAdmin') },
      { path: 'teacher', name: 'Teacher', meta: { name: '教师' }, component: () => import('../views/manager/Teacher') },
      { path: 'student', name: 'Student', meta: { name: '学生' }, component: () => import('../views/manager/Student') },
      { path: 'lab', name: 'Lab', meta: { name: '实验室' }, component: () => import('../views/manager/Lab') },
      { path: 'semester', name: 'Semester', meta: { name: '学期信息' }, component: () => import('../views/manager/Semester') },
      { path: 'stureserve', name: 'StuReserve', meta: { name: '预定信息' }, component: () => import('../views/manager/StuReserve.vue') },
      { path: 'fix', name: 'Fix', meta: { name: '报修信息' }, component: () => import('../views/manager/Fix') },
      { path: 'teaapply', name: 'TeaApply', meta: { name: '实验课申请信息' }, component: () => import('../views/manager/TeaApply.vue') },
    ]
  },
  { path: '/login', name: 'Login', meta: { name: '登录' }, component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', meta: { name: '注册' }, component: () => import('../views/Register.vue') },
  { path: '*', name: 'NotFound', meta: { name: '无法访问' }, component: () => import('../views/404.vue') },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


export default router
