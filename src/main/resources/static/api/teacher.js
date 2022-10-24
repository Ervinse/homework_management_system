//表格数据函数
// 查询教师列表接口
const getTeacherPageAxios = (currentPage,pageSize,searchValue) => {
    return $axios({
        url: '/teacher/page',
        method: 'get',
        params:{
            currentPage: currentPage,
            pageSize: pageSize,
            searchValue: searchValue
        }
    })
}

// 查询教师详情接口
const getTeacherDetailAxios = (teacherId) => {
    return $axios({
        url: '/teacher',
        method: 'get',
        params: {
            teacherId: teacherId
        }
    })
}

// 查询班级搜索列表接口
const getTeacherPageSearchAxios = (params) => {
    return $axios({
        url: '../../json/teacher/teacher_page_search_success.json',
        method: 'get',
        params: {params}
    })
}


//操作接口
//添加接口
const addTeacherAxios = (params) => {
    return $axios({
        url: '/teacher',
        method: 'post',
        data: {...params}
    })
}

//修改接口
const editTeacherAxios = (params) => {
    return $axios({
        url: '/teacher',
        method: 'put',
        data: {...params}
    })
}

//删除函数
const deleteTeacherAxios = (teacherId) => {
    return $axios({
        url: '/teacher',
        method: 'delete',
        params: {
            teacherId: teacherId
        }
    })
}

//账户启用按钮
const enableAccountAxios = (teacherId) => {
    return $axios({
        url: '/teacher/enableAccount',
        method: 'put',
        params: {
            teacherId: teacherId
        }
    })
}

//账户禁用按钮
const disableAccountAxios = (teacherId) => {
    return $axios({
        url: '/teacher/disableAccount',
        method: 'put',
        params: {
            teacherId: teacherId
        }
    })
}