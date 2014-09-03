import Model.BaseData;
import com.yunkuent.sdk.*;
import com.yunkuent.sdk.data.ReturnResult;
import com.yunkuent.sdk.utils.Util;
import org.apache.http.HttpStatus;

/**
 * Created by Brandon on 2014/8/6.
 */
public class SDKTester {
    public static final String UESRNAME = "";
    public static final String PASSWORD = "";
    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";

    private static EntLibManager mEntLibManager;
    private static EntManager mEntManager;
    private static EntFileManager mFileManager;

    public static void main(String[] args) {
        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="D://LogPath";//默认在D盘根目录

        //==========================云库企业库操作==========================//
        mEntLibManager = new EntLibManager(UESRNAME, PASSWORD, CLIENT_ID, CLIENT_SECRET);
//        //获取认证
        mEntLibManager.accessToken(true);

        //创建云库
//        deserializeReturn(mEntLibManager.create("destroy", 10, "destroy", "test lib"));

        //获取库列表
//        deserializeReturn(mEntLibManager.getLibList());

        //获取库授权
//        deserializeReturn(mEntLibManager.bind(48714,"",""));

        //取消库授权
//        deserializeReturn(mEntLibManager.unBind("0b7bd4e22c1a9eb8e3ddba2b6c37f6e2"));

        //添加库成员
//        deserializeReturn(mEntLibManager.addMembers(150998,2892,new int[]{4}));

        //获取某一个库的成员
//        deserializeReturn(mEntLibManager.getMembers(0, 2, 32662));

        //批量修改单库中成员角色
//        deserializeReturn(mEntLibManager.setMemberRole(150998,2894,new int[]{4}));

        //从库中删除成员
//        deserializeReturn(mEntLibManager.delMember(150998,new int[]{4}));

        //获取某一个企业分组列表
//        deserializeReturn(mEntLibManager.getGroups(32657));

        //库上添加分组
//        deserializeReturn(mEntLibManager.addGroup(150998,4448,2892));

        //库上删除分组
//        deserializeReturn(mEntLibManager.delGroup(150998,4448));

        //设置分组上的角色
//        deserializeReturn(mEntLibManager.setGroupRole(150998,4448,2894));

        //删除库
//        deserializeReturn(mEntLibManager.destroy("7b381ec385a96bc9f4e7f3fca01e2351"));

//=======文件操作========//

        String orgClientId = "bab623c8a80283689c6a77fec0ecede1";
        String orgClientSecret = "fd5ba40932fa31dd18170881b9f00e77";
        mFileManager = new EntFileManager(orgClientId, orgClientSecret);
        //获取库中文件
//        deserializeReturn(mFileManager.getFileList((int)Util.getUnixDateline(), 0, ""));

        //获取更新列表
//        deserializeReturn(mFileManager.getUpdateList((int) Util.getUnixDateline(), false, 0));

        //获取文件(夹)信息
//        deserializeReturn(mFileManager.getFileInfo((int) Util.getUnixDateline(),"test"));

        //创建文件夹
//        deserializeReturn(mFileManager.createFolder((int) Util.getUnixDateline(),"test","Brandon"));

        //上传文件 文件不得超过50MB
//        deserializeReturn(mFileManager.createFile((int) Util.getUnixDateline(),"test/test.txt","Brandon","D:\\test.txt"));

        //删除文件
//        deserializeReturn(mFileManager.del((int) Util.getUnixDateline(),"test","Brandon"));

        //移动文件
//        deserializeReturn(mFileManager.move((int) Util.getUnixDateline(),"test/test.txt","test.txt","Brandon"));

        //文件连接
//        deserializeReturn(mFileManager.link( (int) Util.getUnixDateline(), "test.txt"));

        //发送消息
//        deserializeReturn(mFileManager.sendmsg( (int) Util.getUnixDateline(), "msgTest", "msg", "", "", "Brandon"));

        //获取当前库所有外链(new)
//        deserializeReturn(mFileManager.links((int) Util.getUnixDateline(), true));


//==========================云库企业操作==========================//
        mEntManager = new EntManager(UESRNAME, PASSWORD, CLIENT_ID, CLIENT_SECRET);

        //获取认证
//        mEntManager.accessToken(true);

        //获取角色
//        deserializeReturn(mEntManager.getRoles());

        //获取分组
//        deserializeReturn(mEntManager.getGroups());

        //获取成员
//        deserializeReturn(mEntManager.getMembers(0, 2));

        //根据成员id获取成员个人库外链(new)
//        deserializeReturn(mEntManager.getMemberFileLink(52, true));

        //根据外部成员id获取成员信息(new)
//        deserializeReturn(mEntManager.getMemberByOutid(new String[]{"nishuonishuo","dqwdqw"}));

        //添加或修改同步成员
//        deserializeReturn(mEntManager.addSyncMember("MemberTest1", "Member1", "Member1", "", ""));
//        deserializeReturn(mEntManager.addSyncMember("MemberTest2", "Member2", "Member2", "", ""));
//        deserializeReturn(mEntManager.addSyncMember("MemberTest3", "Member3", "Member3", "", ""));

        //添加或修改同步分组
//        deserializeReturn(mEntManager.delSyncMember(new String[]{"MemberTest", "MemberTest1", "MemberTest2"}));

//        deserializeReturn(mEntManager.addSyncGroup("ParentGroup", "ParentGroup", ""));
//        deserializeReturn(mEntManager.addSyncGroup("GroupTest", "Group", "ParentGroup"));


        //删除同步分组
//        deserializeReturn(mEntManager.delSyncGroup(new String[] { "ParentGroup", "GroupTest" }));

        //添加同步分组的成员

//        deserializeReturn(mEntManager.addSyncGroupMember("GroupTest",new String[]{"MemberTest1"}));
//        deserializeReturn(mEntManager.addSyncGroupMember("ParentGroup", new String[] { "MemberTest2","MemberTest3" }));

        //删除同步分组的成员
//        deserializeReturn(mEntManager.delSyncGroupMember("ParentGroup", new String[] { "MemberTest2", "MemberTest3" }));
    }

    /**
     * 解析返回内容
     *
     * @param result
     */
    private static void deserializeReturn(String result) {

        //解析结果
        ReturnResult returnResult = ReturnResult.create(result);

        if (returnResult.getStatusCode() == HttpStatus.SC_OK) {
            //成功的结果
            System.out.println("return 200");

        } else {
            //解析result中的内容
            BaseData data = BaseData.create(returnResult.getResult());
            if (data != null) {
                //如果可解析，则返回错误信息和错误号
                System.out.println(data.getErrorCode() + ":" + data.getErrorMsg());
            }
        }
        System.out.println(returnResult.getResult());
        //复制到剪贴板
        Util.copyToClipboard(returnResult.getResult());
    }


}

