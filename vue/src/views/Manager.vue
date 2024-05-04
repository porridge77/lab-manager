<template>
  <div class="manager-container">
    <!--  头部  -->
    <div class="manager-header">
      <div class="manager-header-left">
        <i class="el-icon-s-platform" style="font-size: 30px; font-weight: 1000"></i>
        <div class="title">实验室管理平台</div>
      </div>

      <div class="manager-header-center">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: $route.path }">{{ $route.meta.name }}</el-breadcrumb-item>
        </el-breadcrumb>
        <div style="margin-left: 200px; color: #8c939d">
          <h3>当前学期:{{currentSemesterName}}</h3>
        </div>
      </div>

      <div class="manager-header-right">
        <el-dropdown placement="bottom">
          <div class="avatar">
            <img :src="'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" />
            <div>{{ user.name ||  '管理员' }}</div>
          </div>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>

    <!--  主体  -->
    <div class="manager-main">
      <!--  侧边栏  -->
      <div class="manager-main-left">
        <el-menu :default-openeds="['info', 'user']" router style="border: none" :default-active="$route.path">
          <el-menu-item index="/home">
            <i class="el-icon-s-home"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
          <el-menu-item index="/stureserve" v-if="user.role === 'STUDENT'">
            <i class="el-icon-menu"></i>
            <span slot="title">预订信息</span>
          </el-menu-item>
          <el-menu-item index="/fix" v-if="user.role === 'LABADMIN' || user.role === 'TEACHER'">
            <i class="el-icon-menu"></i>
            <span slot="title">报修信息</span>
          </el-menu-item>
          <el-menu-item index="/teaapply" v-if="user.role === 'TEACHER'">
            <i class="el-icon-setting"></i>
            <span slot="title">实验课申请</span>
          </el-menu-item>
          <el-submenu index="info" v-if="user.role === 'ADMIN'">
            <template slot="title">
              <i class="el-icon-menu"></i><span>信息管理</span>
            </template>
            <el-menu-item index="/semester">学期信息</el-menu-item>
            <el-menu-item index="/lab">实验室信息</el-menu-item>
            <el-menu-item index="/teaapply">实验课申请</el-menu-item>
            <el-menu-item index="/stureserve">实验室借用申请</el-menu-item>
          </el-submenu>

          <el-submenu index="user" v-if="user.role === 'ADMIN'">
            <template slot="title">
              <i class="el-icon-menu"></i><span>用户管理</span>
            </template>
            <el-menu-item index="/admin">管理员信息</el-menu-item>
            <el-menu-item index="/labadmin">实验室管理员</el-menu-item>
            <el-menu-item index="/teacher">教师</el-menu-item>
            <el-menu-item index="/student">学生</el-menu-item>
          </el-submenu>
        </el-menu>
      </div>

      <!--  数据表格  -->
      <div class="manager-main-right">
        <router-view @update:user="updateUser" />
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "Manager",
  data() {
    return {
      currentSemesterName:'',
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
    }
  },
  created() {
    this.getCurrentSemester()
    if (!this.user.id) {
      this.$router.push('/login')
    }
  },
  methods: {
    updateUser() {
      this.user = JSON.parse(localStorage.getItem('xm-user') || '{}')   // 重新获取下用户的最新信息
    },
    goToPerson() {
      if (this.user.role === 'ADMIN') {
        this.$router.push('/adminPerson')
      }
    },
    getCurrentSemester() {
      this.$request.get('/semester/current')
          .then(res => {
            this.currentSemesterName = res.data
          })
    },
    logout() {
      localStorage.removeItem('xm-user')
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
@import "@/assets/css/manager.css";
</style>