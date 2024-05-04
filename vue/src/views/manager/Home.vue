<template>
  <div>
    <el-card class="box-card" shadow="hover">
      <span>
        当前, 是第
        <el-select v-model="selectedWeek" placeholder="1" size="small">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            />
          </el-select>
          周的星期
        <el-select v-model="selectedDay" placeholder="一" size="small">
          <el-option v-for="item in days"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value"
          />
        </el-select>
      </span>

      <el-table :data="filteredTableData" border
                style="margin-top:15px; overflow-x:hidden">
        <el-table-column prop="name" label="课程">
        </el-table-column>
        <el-table-column prop="className" label="班级"></el-table-column>
        <el-table-column prop="teacherName" label="教师"></el-table-column>
        <el-table-column prop="section" label="节次"></el-table-column>
        <el-table-column prop="labNo" label="实验室编号"></el-table-column>
      </el-table>
    </el-card>

  </div>
</template>

<script>

export default {
  name: 'Home',
  data() {
    return {
      tableData: [],
      selectedWeek: 1,
      selectedDay: '星期一',
      currentSemester:'',
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      days:[
        { value: '星期一', label: '一' },
        { value: '星期二', label: '二' },
        { value: '星期三', label: '三' },
        { value: '星期四', label: '四' },
        { value: '星期五', label: '五' }
      ],
      options:[
        {
          value: 1,
          label: 1,
        },
        {
          value: 2,
          label: 2,
        },
        {
          value: 3,
          label: 3,
        },
        {
          value: 4,
          label: 4,
        },
        {
          value: 5,
          label: 5,
        },
        {
          value: 6,
          label: 6,
        },
        {
          value: 7,
          label: 7,
        },
        {
          value: 8,
          label: 8,
        },
        {
          value: 9,
          label: 9,
        },
        {
          value: 10,
          label: 10,
        },
        {
          value: 11,
          label: 11,
        },
        {
          value: 12,
          label: 12,
        },
        {
          value: 13,
          label: 13,
        },
        {
          value: 14,
          label: 14,
        },
        {
          value: 15,
          label: 15,
        },
        {
          value: 16,
          label: 16,
        },
        {
          value: 17,
          label: 17,
        },
        {
          value: 18,
          label: 18,
        },
        {
          value: 19,
          label: 19,
        },
        {
          value: 20,
          label: 20,
        },
      ],
    }
  },
  created() {
      this.load()
    this.getCurrentSemester()
  },
  computed: {
    filteredTableData() {
      return this.tableData.filter(item => (item.week === this.selectedWeek)
          && (item.semesterName === this.currentSemester)
      &&(item.dayOfWeek === this.selectedDay));
    }

  },
  methods: {
    load() {
      this.$request.get('/lesson/selectAll').then(res => {
          this.tableData = res.data
      })
    },
    getCurrentSemester() {
      this.$request.get('/semester/current')
          .then(res => {
            this.currentSemester = res.data
            console.log(this.currentSemester)
          })
    },

  }
}
</script>
