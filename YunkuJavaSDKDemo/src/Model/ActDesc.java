package Model;

/**
 * 转化更新列表中的act
 */
public class ActDesc {
    public static String convertAct(int act){
        String actDesc = "";
        switch (act) {
            case 0:
                actDesc="删除";
                break;
            case 1:
                actDesc = "新增";
                break;
            case 2:
                actDesc = "重命名";
                break;
            case 3:
                actDesc = "修改";
                break;
            case 4:
                actDesc = "移动";
                break;
            case 5:
                actDesc = "恢复删除";
                break;
            case 6:
                actDesc = "修改还原";
                break;
            case 16:
                actDesc = "讨论";
                break;
            case 100:
                actDesc = "团队成员离开";
                break;
            case 101:
                actDesc = "团队成员加入";
                break;
            case 110:
                actDesc = "不再是联系人";
                break;
            case 111:
                actDesc = "成为联系人";
                break;
            case 997:
                actDesc = "关闭共享";
                break;
            case 998:
                actDesc = "退出共享";
                break;
            case 999:
                actDesc = "取消共享";
                break;
            case 1000:
                actDesc = "共享文件/文件夹";
                break;
            case 1010:
                actDesc = "链接被下载";
                break;
            case 1011:
                actDesc = "链接被保存到够快";
                break;
            case 1012:
                actDesc = "链接被保存到够快";
                break;
            case 1013:
                actDesc = "链接文件夹内有新文件上传";
                break;
        }
        return actDesc;
    }
}
