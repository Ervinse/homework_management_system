//表格数据函数
// 查询学生列表接口
const getStudentPageAxios = () => {
    return $axios({
        url: '../../json/student/student_page_success.json',
        method: 'get',
    })
}

// 查询学生详情接口
const getStudentDetailAxios = (studentId) => {
    return $axios({
        url: '../../json/student/student_select_success.json',
        method: 'get',
        params: {
            studentId: studentId
        }
    })
}

// 查询班级搜索列表接口
const getStudentPageSearchAxios = (params) => {
    return $axios({
        url: '../../json/student/student_page_search_success.json',
        method: 'get',
        params: {params}
    })
}


//操作接口
//修改接口
const editStudentAxios = (params) => {
    return $axios({
        url: '../../json/common/success.json',
        method: 'post',
        data: {...params}
    })
}
//删除函数
const deleteStudentAxios = (studentId) => {
    return $axios({
        url: '../../json/common/success.json',
        method: 'get',
        params: {
            studentId: studentId
        }
    })
}