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
        url: '../../json/clase/clase_list_success.json',
        method: 'get',
    })
}

// 查询学生集合接口
const getStudentListAxios = () => {
    return $axios({
        url: '../../json/student/student_list_success.json',
        method: 'get',
    })
}

// 查询课程集合接口
const getCourseListAxios = () => {
    return $axios({
        url: '../../json/course/course_list_success.json',
        method: 'get',
    })
}

//图片相关接口
//图片上传接口
const imageUploadUrl = '../../json/common/image_success.json';
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