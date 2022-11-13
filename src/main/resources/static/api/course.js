//表格数据函数
// 查询课程列表接口
const getCoursePageAxios = (currentPage,pageSize,searchValue) => {
    return $axios({
        url: '/course/page',
        method: 'get',
        params:{
            currentPage: currentPage,
            pageSize: pageSize,
            searchValue: searchValue
        }
    })
}

// 根据学生id查询课程列表接口(学生端)
const getCoursePageByStudentIdAxios = (currentPage,pageSize,studentId) => {
    return $axios({
        url: '/course/pageByStudent',
        method: 'get',
        params:{
            currentPage: currentPage,
            pageSize: pageSize,
            studentId: studentId
        }
    })
}


// 查询课程详情接口
const getCourseDetailAxios = (courseId) => {
    return $axios({
        url: '/course',
        method: 'get',
        params: {
            courseId: courseId
        }
    })
}

//按钮处理函数
//添加接口
const addCourseAxios = (params) => {
    return $axios({
        url: '/course',
        method: 'post',
        data: { ...params }
    })
}

//需改接口
const updateCourseAxios = (params) => {
    return $axios({
        url: '/course',
        method: 'put',
        data: { ...params }
    })
}

//删除函数
const deleteCourseAxios = (courseId) => {
    return $axios({
        url: '/course',
        method: 'delete',
        params: {
            courseId: courseId
        }
    })
}