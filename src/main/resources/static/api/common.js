//候选数据查询接口
// 查询老师集合接口
const getTeacherListAxios = () => {
    return $axios({
        url: '/teacher/list',
        method: 'get',
    })
}

// 查询班级集合接口
const getClaseListAxios = () => {
    return $axios({
        url: '/clase/list',
        method: 'get',
    })
}

// 查询学生集合接口
const getStudentListAxios = () => {
    return $axios({
        url: '/student/list',
        method: 'get',
    })
}

// 查询课程集合接口
const getCourseListAxios = () => {
    return $axios({
        url: '/course/list',
        method: 'get',
    })
}

//图片相关接口
//图片上传接口
const imageUploadUrl = '/common/uploadImage';
//文件上传接口
const fileUploadUrl = '../../json/common/file_success.json';

//移除图片接口
const removeImageAxios = (fileName) => {
    return $axios({
        url: '../../json/common/success.json',
        method: 'get',
        params: {
            fileName: fileName
        }
    })
}

//移除图片接口
const removeFileAxios = (fileName) => {
    return $axios({
        url: '../../json/common/success.json',
        method: 'get',
        params: {
            fileName: fileName
        }
    })
}

const logoutAxios = () => {
    return $axios({
        url: '/login/logout',
        method: 'post',
    })
}