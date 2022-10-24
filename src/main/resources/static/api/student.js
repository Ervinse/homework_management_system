//表格数据函数
// 查询学生列表接口
const getStudentPageAxios = (currentPage,pageSize,searchValue) => {
    return $axios({
        url: '/student/page',
        method: 'get',
        params:{
            currentPage: currentPage,
            pageSize: pageSize,
            searchValue: searchValue
        }
    })
}

// 查询学生详情接口
const getStudentDetailAxios = (studentId) => {
    return $axios({
        url: '/student',
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
        url: '/student',
        method: 'put',
        data: {...params}
    })
}
//删除函数
const deleteStudentAxios = (studentId) => {
    return $axios({
        url: '/student',
        method: 'delete',
        params: {
            studentId: studentId
        }
    })
}