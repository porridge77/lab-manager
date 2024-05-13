<template>
  <div>

    <div class="operation" v-if="user.role === 'TEACHER'">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe  @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="teacherName" label="报修人" show-overflow-tooltip></el-table-column>
        <el-table-column prop="description" label="故障描述" show-overflow-tooltip></el-table-column>
        <el-table-column prop="date" label="报修日期" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ formatDate(scope.row.date) }}
          </template>
        </el-table-column>
        <el-table-column prop="labNo" label="实验室编号" show-overflow-tooltip></el-table-column>
        <el-table-column prop="labAdminName" label="实验室管理员" show-overflow-tooltip></el-table-column>
        <el-table-column prop="status" label="报修状态" show-overflow-tooltip></el-table-column>
        <el-table-column prop="statement" label="维修情况说明" show-overflow-tooltip></el-table-column>

        <el-table-column label="操作" width="180" align="center" >
          <template v-slot="scope">
            <el-button plain type="primary" @click="handleEdit(scope.row)" size="mini" v-if="user.role === 'TEACHER'">编辑</el-button>
            <el-button plain type="danger" size="mini" @click=del(scope.row.id) v-if="user.role === 'TEACHER'">删除</el-button>
            <el-button plain type="warning" size="mini" @click="changeStatus(scope.row, '维修中')" v-if="user.role == 'LABADMIN' && scope.row.status === '未维修'">开始维修</el-button>
            <el-button plain type="success" size="mini" @click="changeStatus(scope.row, '已维修')" v-if="user.role == 'LABADMIN' && scope.row.status === '维修中'" style="margin-bottom: 5px">维修完成</el-button>
            <el-button plain type="primary" size="mini" @click="handleEdit(scope.row)" v-if="user.role == 'LABADMIN' && scope.row.status === '维修中'">补充维修情况</el-button>

          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total">
        </el-pagination>
      </div>
    </div>

    <el-dialog title="报修" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="labId" label="实验室编号">
          <el-select v-model="form.labId" placeholder="请选择实验室" style="width: 100%">
            <el-option v-for="item in labData" :label="item.no" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="description" label="故障描述" v-if="form.status === '未维修'||form.status === NULL">
          <el-input v-model="form.description" autocomplete="off" type="textarea"
                    :rows="2"></el-input>
        </el-form-item>
        <el-form-item prop="date" label="日期" v-if="form.status === '未维修'||form.status === NULL">
          <el-date-picker v-model="form.date" type="date" placeholder="选择日期" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd" style="width:385px"></el-date-picker>
        </el-form-item>
        <el-form-item prop="statement" label="维修情况说明" v-if="form.status === '维修中'">
          <el-input type="textarea"
                    :rows="2"
                    placeholder="请输入内容"
                    v-model="form.statement"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>

import moment from "moment";

export default {
  name: "Fix",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      name: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        labId: [
          {required: true, message: '请选择实验室', trigger: 'change'},
        ],
        description: [
          {required: true, message: '请填写故障描述', trigger: 'blur'},
        ],
        date: [
          {required: true, message: '请选择日期', trigger: 'change'},
        ],
      },
      ids: [],
      labData: [],
    }
  },
  created() {
    this.load(1)
    this.loadLab()
  },
  methods: {
    loadLab(){
      this.$request.get('/lab/selectAll').then(res => {
        if (res.code === '200') {
          this.labData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    formatDate(dateStr) {
      return moment(dateStr).format('YYYY-MM-DD'); // 使用 Moment.js 格式化日期
    },
    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.fromVisible = true   // 打开弹窗
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
    },
    changeStatus(row, status) {
      let data = JSON.parse(JSON.stringify(row))
      if ('已维修' === status) {
        data.status = status
      }
      if ('未维修' === status) {
        data.status = status
      }
      if ('维修中' === status) {
        data.status = status
      }
      this.$request.put('/fix/update', data).then(res => {
        if (res.code === '200') {
          this.$message.success('操作成功')
          this.load(1)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      if(this.user.role === 'TEACHER'){
        this.form.teacherId = this.user.id
      }
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/fix/update' : '/fix/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form,
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/fix/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)   //  [1,2]
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/fix/delete/batch', {data: this.ids}).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    load(pageNum) {  // 分页查询

      if (pageNum) this.pageNum = pageNum
      this.$request.get('/fix/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.name = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
  }
}
</script>

<style scoped>

</style>
