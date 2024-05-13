<template>
  <div>
    <div class="search">
      <el-select v-model="status" placeholder="请选择审核状态" style="width: 200px">
        <el-option label="待审核" value="待审核"></el-option>
        <el-option label="审核通过" value="审核通过"></el-option>
        <el-option label="审核不通过" value="审核不通过"></el-option>
      </el-select>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation" v-if="user.role === 'STUDENT'">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe>
        <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="labNo" label="实验室" show-overflow-tooltip></el-table-column>
        <el-table-column prop="semesterName" label="学期" show-overflow-tooltip></el-table-column>
        <el-table-column prop="week" label="周数" show-overflow-tooltip></el-table-column>
        <el-table-column prop="section" label="节次" show-overflow-tooltip></el-table-column>
        <el-table-column prop="reason" label="原因" show-overflow-tooltip></el-table-column>
        <el-table-column prop="date" label="申请日期" show-overflow-tooltip></el-table-column>
        <el-table-column prop="studentName" label="预约人" show-overflow-tooltip></el-table-column>
        <el-table-column prop="status" label="预约状态"></el-table-column>

        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="warning" size="mini" @click="del(scope.row.id)" v-if="user.role === 'STUDENT' && scope.row.status === '未审核'">取消预约</el-button>
            <el-button plain type="warning" size="mini" @click="changeStatus(scope.row, '通过')" v-if="user.role !== 'STUDENT' && scope.row.status === '未审核'">通过</el-button>
            <el-button plain type="warning" size="mini" @click="changeStatus(scope.row, '驳回')" v-if="user.role !== 'STUDENT' && scope.row.status === '未审核'">驳回</el-button>
            <el-button plain type="warning" size="mini" @click="changeStatus(scope.row, '使用完毕')" v-if="scope.row.status === '通过'">结束使用</el-button>
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

    <el-dialog title="预约" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="labId" label="实验室编号">
          <el-select v-model="form.labId" placeholder="请选择实验室" style="width: 100%">
            <el-option v-for="item in labData" :label="item.no" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="week" label="周次">
          <el-input v-model="form.week" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="section" label="节次">
          <el-select v-model="form.section" placeholder="请选择节次" style="width:385px;">
            <el-option
                v-for="item in sectionOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="date" label="日期">
          <el-date-picker v-model="form.date" type="date" placeholder="选择日期" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd" style="width:385px;"></el-date-picker>
        </el-form-item>
        <el-form-item prop="reason" label="原因">
          <el-input v-model="form.reason" autocomplete="off" type="textarea"
                    :rows="2"></el-input>
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
export default {
  name: "StuReservation",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      status: null,
      section:'',
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        labId: [
          {required: true, message: '请选择实验室', trigger: 'change'},
        ],
        week: [
          {required: true, message: '请输入周次', trigger: 'blur'},
        ],
        section: [
          {required: true, message:'请选择节次', trigger:'change'},
        ],
        date: [
          {required: true, message:'请选择日期', trigger:'change'},
        ],
      },
      sectionOptions: [
        { value: '1-2', label: '1-2' },
        { value: '3-4', label: '3-4' },
        { value: '5-6', label: '5-6' },
        { value: '7-8', label: '7-8' },
      ],
      ids: [],
      labData: [],
    }
  },
  created() {
    this.getCurrentSemester();
    this.load(1);
    this.loadLab();
  },
  methods: {
    loadLab(){
      this.$request.get('/lab/selectAll').then(res => {
        if (res.code === '200') {
          this.labData = res.data
          console.log(res.data)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAdd() {   // 新增数据
      this.loadLab()
      this.form = {}  // 新增数据的时候清空数据
      this.fromVisible = true   // 打开弹窗
    },
    getCurrentSemester() {
      this.$request.get('/semester/current')
          .then(res => {
            this.form.semesterName = res.data
          })
    },
    changeStatus(row, status) {
      let data = JSON.parse(JSON.stringify(row))
      if ('通过' === status) {
        data.status = status
      }
      if ('驳回' === status) {
        data.status = status
      }
      if ('使用完毕' === status) {
        data.status = '使用完毕'
      }
      this.$request.put('/stureservation/update', data).then(res => {
        if (res.code === '200') {
          this.$message.success('操作成功')
          this.load(1)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.form.studentId = this.user.id
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/stureservation/update' : '/stureservation/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
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
      this.$confirm('您确定取消预约吗？', '灵魂拷问', {type: "warning"}).then(response => {
        this.$request.delete('/stureservation/delete/' + id).then(res => {
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
      this.$request.get('/stureservation/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          status: this.status,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.status = null
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
