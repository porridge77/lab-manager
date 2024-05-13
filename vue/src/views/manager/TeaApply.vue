<template>
  <div>
    <div class="search">
      <el-select v-model="status" placeholder="请选择排课状态" style="width: 200px">
        <el-option label="未排课" value="未排课"></el-option>
        <el-option label="已排课" value="已排课"></el-option>
      </el-select>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation" v-if="user.role === 'TEACHER'">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe>
        <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="semesterName" label="学期" show-overflow-tooltip></el-table-column>
        <el-table-column prop="teacherName" label="申请人" show-overflow-tooltip></el-table-column>
        <el-table-column prop="type" label="实验室类型" show-overflow-tooltip></el-table-column>
        <el-table-column prop="name" label="课程名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="className" label="班级" show-overflow-tooltip></el-table-column>
        <el-table-column prop="number" label="人数" show-overflow-tooltip></el-table-column>
        <el-table-column prop="start" label="起始周" show-overflow-tooltip></el-table-column>
        <el-table-column prop="end" label="结束周" show-overflow-tooltip></el-table-column>
        <el-table-column prop="dayOfWeek" label="星期几" show-overflow-tooltip></el-table-column>
        <el-table-column prop="section" label="节次" show-overflow-tooltip></el-table-column>
        <el-table-column prop="status" label="排课状态"></el-table-column>

        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="warning" size="mini" @click="del(scope.row.id)" v-if="user.role === 'TEACHER' && scope.row.status === '未排课'">撤回</el-button>
            <el-button plain type="warning" size="mini" @click="handleEdit(scope.row)" v-if="user.role === 'TEACHER' && scope.row.status === '未排课'">修改申请</el-button>
            <el-button plain type="warning" size="mini" @click="arrange(scope.row, '已排课')" v-if="user.role == 'ADMIN' && scope.row.status === '未排课'">排课</el-button>
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

    <el-dialog title="实验课申请登记" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="name" label="课程名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="type" label="实验室类型">
          <el-select v-model="form.type" placeholder="请选择" style="width:385px;">
            <el-option label="硬件" value="硬件"></el-option>
            <el-option label="软件" value="软件"></el-option>
            <el-option label="网络" value="网络"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="className" label="班级">
          <el-input v-model="form.className" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="number" label="人数">
          <el-input v-model="form.number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="start" label="起始周">
          <el-input v-model="form.start" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="end" label="结束周">
          <el-input v-model="form.end" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="dayOfWeek" label="星期几">
          <el-select v-model="form.dayOfWeek" placeholder="请选择" style="width:385px;">
            <el-option label="星期一" value="星期一"></el-option>
            <el-option label="星期二" value="星期二"></el-option>
            <el-option label="星期三" value="星期三"></el-option>
            <el-option label="星期四" value="星期四"></el-option>
            <el-option label="星期五" value="星期五"></el-option>
          </el-select>
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
  name: "TeaApply",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      status: null,
      fromVisible: false,
      section:'',
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [
          {required: true, message: '请输入课程名称', trigger: 'blur'},
        ],
        type: [
          {required: true, message: '请选择类型', trigger: 'change'},
        ],
        className: [
          {required: true, message: '请输入班级名称', trigger: 'blur'},
        ],
        number: [
          {required: true, message: '请输入班级人数', trigger: 'blur'},
        ],
        start: [
          {required: true, message: '请输入起始周', trigger: 'blur'},
        ],
        end: [
          {required: true, message: '请输入结束周', trigger: 'blur'},
        ],
        dayOfWeek: [
          {required: true, message: '请选择', trigger: 'change'},
        ],
        section: [
          {required: true, message: '请选择节次', trigger: 'change'},
        ],
      },
      ids: [],
      sectionOptions: [
        { value: '1-2', label: '1-2' },
        { value: '3-4', label: '3-4' },
        { value: '5-6', label: '5-6' },
        { value: '7-8', label: '7-8' },
      ],
    }
  },
  created() {
    this.getCurrentSemester()
    this.load(1)
  },
  methods: {
    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.fromVisible = true   // 打开弹窗
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
    },
    getCurrentSemester() {
      this.$request.get('/semester/current')
          .then(res => {
            this.form.semesterName = res.data
          })
    },
    arrange(row){
      let data = JSON.parse(JSON.stringify(row))
      data.status = '已排课'
      this.arrangeLesson(data.id)
      this.$request.put('/teaapplication/update', data).then(res => {
        if (res.code === '200') {
          this.$message.success('操作成功')
          this.load(1)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    arrangeLesson(id){
      this.$request.post('/lesson/add/'+id).then(res =>{
        if (res.code === '200') {
          console.log('请查看排课表');
        } else {
          this.$message.error(res.msg);
        }
          })
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.form.teacherId = this.user.id
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/teaapplication/update' : '/teaapplication/add',
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
      this.$confirm('您确定取消申请吗？', '灵魂拷问', {type: "warning"}).then(response => {
        this.$request.delete('/teaapplication/delete/' + id).then(res => {
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
      this.$request.get('/teaapplication/selectPage', {
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
