import java.util.ArrayList;

public class QQinfo{
		public String QQ,nickname;
		public ArrayList QQFriends = null; 
		QQinfo(String QQnum,String nickName){
			QQ = QQnum;
			nickname = nickName;
			QQFriends = new ArrayList();
		}
		void addFriends(QQinfo xxx){
			QQFriends.add(xxx);
		}
	}