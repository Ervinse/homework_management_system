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

// 查询课程搜索列表接口
const getCoursePageSearchAxios = (params) => {
    return $axios({
        url: '../../json/course/course_page_search_success.json',
        method: 'get',
        params: {params}
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