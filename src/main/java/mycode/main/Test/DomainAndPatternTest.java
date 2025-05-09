package mycode.main.Test;

import java.util.Arrays;
import java.util.List;

public class DomainAndPatternTest {
    
    private static String domain = "INSERT INTO Message_Blocking.dbo.DomainWhiteList (SeqNo, GroupID, DestCategory, UserName, Domain, Operator, LastStamp) \nSELECT NEXT VALUE FOR Message_Blocking.dbo.Seq_DomainWhiteList, 'MSG', '漁夫數位', '', '%s', 'system', GETDATE()";
    private static String pattern = "INSERT INTO Message_Blocking.dbo.KeyWordWhiteListByPattern(SeqNo, GroupID, DestCategory, UserName, Keyword_RE, Operator, LastStamp) \nSELECT NEXT VALUE FOR Message_Blocking.dbo.Seq_KeyWordWhiteListByPattern, 'MSG', '漁夫數位',  '', N'%s', 'system', GETDATE()";

    public static void main(String[] args) {
//        domain_data.forEach(data -> System.out.println(String.format(domain, data)));
        pattern_data.forEach(data -> System.out.println(String.format(pattern, data)));
    }

    static List<String> domain_data = Arrays.asList(
            "www.aph.tw",
            "lfntu.com",
            "www.aph.tw",
            "www.ddhappy.net",
            "cchengroad.com",
            "www.bblens.tw",
            "www.jgbsmart.com",
            "qmomo.shop",
            "r8tw.cc",
            "abab.one",
            "da388.net",
            "hehume.com",
            "taichi-tshirt.net",
            "gbf.tw",
            "tpii.com.tw",
            "r.zecz.ec",
            "sun.hchs.ntpc.edu.tw",
            "help-dog-cat.waca.tw",
            "to.zoeyinusa.com",
            "mingyi.page.link",
            "n2.zuvio.com.tw",
            "www.beefarmer.tw",
            "www.papawaqa.com.tw",
            "sh178.net",
            "kbb.tw",
            "kf6.me",
            "m.5168th.com",
            "www.jjfish.com.tw",
            "una5885.net",
            "mangoccc.com",
            "www.0938011899.com.tw",
            "r.zecz.ec",
            "ezchance.com.tw",
            "www.host886.com",
            "www.25431010.tw",
            "aura-shop.tw",
            "www.fooddog999.com.tw",
            "www.cshm.org.tw",
            "v2.booknow.tw",
            "eschool.tp.edu.tw",
            "ipc.kcsat.org",
            "www.brother-office.com.tw",
            "www.hida.com.tw",
            "m.bu5168.com",
            "trustpay.hitrust.com.tw",
            "www.lifecode.com.tw",
            "www.rutenmall.com",
            "ojt.wda.gov.tw",
            "www.beclass.com",
            "www.beape.com.tw",
            "akikojr.com",
            "dms.bmdiot.com",
            "www.hcgec.com.tw",
            "survey.eao.fju.edu.twfju.edu.tw",
            "cookpot.tw",
            "na.com.tw",
            "best-oyster.com",
            "jptip.cc",
            "warx.shop",
            "nthurc.jgbsmart.com",
            "www.gras.tw",
            "us06web.zoom.us",
            "www.etalkingonline.com",
            "school.taishinbank.com.tw"
    );

    static List<String> pattern_data = Arrays.asList(
            ".*3M醫材網.*立即選購.*bit.ly/.*",
            ".*3M醫材網.*立即選購.*pse.is/.*",
            ".*88節限時特惠.*明星商品.*熱銷強勢預購中.*lihi2.com/.*",
            ".*Adidas Reopen.*Info.*https://pse.is.*",
            ".*ATM繳費代碼.*金額.*客服https://lin.ee/.*",
            ".*BB.STUDIO藝術寫真.*官方LINE.*",
            ".*CICIZA.*官方LINE.*https://page.line.me/ciciza.*",
            ".*Dr.foot.*門市.*06.*2970685.*",
            ".*https://line.me/.*教務處承辦人.*校內分機1602.*",
            ".*https://school.ctbcbank.com/.*中國信託.*繳費.*宿舍.*",
            ".*ID286藝術寫真.*官方LINE.*https://liff.line.me/.*",
            ".*ID286藝術寫真.*官方LINE.*https://lin.ee/.*",
            ".*Nike store.*FB.*https://reurl.cc/.*",
            ".*Nike store.*Info.*https://pse.is/.*",
            ".*NIKE STORE.*info.*https://pse.is/.*",
            ".*Nike store.*Info:https://lurl.cc/.*",
            ".*reset your password.*https://auragem.com.tw/.*",
            ".*VSL#3益生菌.*reurl.cc/.*",
            ".*WARX機能除臭襪.*兌換序號.*兌換流程.*https://nevent.family.com.tw/fami_pin.*兌換時間為.*止",
            ".*ybt0126d.*https://lin.ee/.*新時代眼鏡風格店.*",
            ".*三五階正副家長培訓班.*心成LINE.*https://line.me/.*",
            ".*心成能量學.*心成LINE.*https://line.me/.*",
            ".*台南應用科大.*https://line.me/.*",
            ".*本次入住日期.*總共.*晚.*館內注意事項.*.*GOGO HOTEL.*",
            ".*生命蛻變.*戰勝惡習.*場.*行前通知.*https://line.me/.*",
            ".*禾場國際芳療學苑.*官方諮詢窗口.*https://lin.ee/.*",
            ".*先取消您的.*傳愛領袖.*資格.*心成LINE.*https://line.me/.*",
            ".*全家繳費代碼.*金額.*客服https://lin.ee/.*",
            ".*全適能.*https://lin.ee/8uLsGL8.*",
            ".*名富餐廳.*菜單.*https://shorturl.at/.*04.*7517555.*",
            ".*名富餐廳.*菜單https://bit.ly/.*04.*7517555.*",
            ".*好客民宿擴大能量.*說明會.*https://reurl.cc/.*",
            ".*好客民宿擴大量能說明會.*活動詳情.*https://reurl.cc/.*",
            ".*好時光.*https://bit.ly/.*",
            ".*好時光.*https://lin.ee/.*",
            ".*好時光.*https://maac.io/.*",
            ".*年結業型農.*問卷連結.*https://reurl.cc/.*",
            ".*老師.*pse.is/.*搭配地政士換證時數04.*22213528.*",
            ".*行前通知.*地質興味旅.*當日活動聯絡人.*詳細資訊請參考連結.*https://reurl.cc/.*",
            ".*佐佐鮮滿意度調查.*https://lihi2.com/.*",
            ".*奇摩吉運動休閒館.*結帳出示簡訊畫面.*更多資訊.*https://pse.is.*",
            ".*奇摩吉運動休閒館.*感謝祭.*結帳出示簡訊畫面單筆享.*優惠.*更多資訊.*https://lurl.cc/.*",
            ".*官方LINE.* https://goo.gl/.*TWAA台灣芳療協會.*",
            ".*官方LINE.*https://goo.gl/.*禾場國際芳療學苑.*",
            ".*法多多體驗回饋.*問券填寫.*lihi2.com/.*",
            ".*芳療.*入門班.*開課.*http://line.me/.*",
            ".*金色三麥.*店.*https://drive.google.com/.*金色三麥.*店很高興為您服務，謝謝。",
            ".*長照課程.*LINE.*https://lin.ee/.*",
            ".*訂單編號.*於.*完成.*繳費.*客服https://lin.ee/.*",
            ".*訂單編號.*於07/04.*繳費.*客服https://lin.ee/.*",
            ".*家長您好.*老師好友或搜尋Line ID.*https://line.me/.*",
            ".*恭喜您於.*活動獲.*加入LINE.*reurl.cc/.*0266315022.*",
            ".*健康長行.*bit.ly/.*",
            ".*健康長行.*http://nav.cx/.*0800076868.*",
            ".*健康長行.*https://lihi.cc/.*",
            ".*健康長行.*https://reurl.cc.*",
            ".*參加.*LINE.*https://lin.ee/.*04.*36011838.*",
            ".*唯醫圈.*立即搶購.*pse.is/.*",
            ".*培力藥品.*月月私語.*https://reurl.cc.*",
            ".*崙禾VIP尊榮客戶專屬活動.*Line官方.*https://lin.ee/.*",
            ".*帳號.*到期.*繼續.*捐款.*lovecom.org/product/lcfamily.*",
            ".*您.*報名成功.*舉行.*課程.*開始報到.*準時開始.*心成.*https://line.me/.*",
            ".*您好.*訂購.*商品已到.*門市.*bit.ly/.*",
            ".*您好.*這是.*客服.*可加LINE.*https://line.me/.*",
            ".*您好.*驗證碼為6位數字.*請確認網頁識別碼.*",
            ".*您有報名.*官方Line.*@473jebmf.*https://lin.ee/.*02.*2564.*2575.*",
            ".*您位於.*已被人登入.*請查明是否為您本人登入.*登入IP位置.*",
            ".*您於.*訂單.*付款未完成.*重新付款.*bit.ly/.*",
            ".*您報名.*課程.*官方LINE.*https://lin.ee/.*04.*36011838.*",
            ".*您購買商品開立的電子發票號碼.*中獎.*元.*請至.*https://bit.ly/.*依照步驟填寫收件資料.*本公司將會寄出實體統一發票提供您領獎.*請務必於.*前點連結填寫您的收件資料.*以免作業上來不及領取獎項.*",
            ".*教務處承辦人.*https://reurl.cc/.*教務處承辦人.*校內分機1602.*",
            ".*曼漫拾光.*https://lin.ee.*",
            ".*曼漫拾光.*https://page.line.me/.*",
            ".*通知您的繳費已逾期.*如需重新繳費.*操作請見.*https://fsurl.tw/.*",
            ".*通知您購買的.*取貨編號.*全家.*店.*截止日.*金額.*元敬請留意取件感謝",
            ".*無光晚餐.*盲吃饗宴.*https://shorturl.at/Tmy8d",
            ".*註冊未完成緊急通知.*問卷.*https://forms.gle/.*",
            ".*開課通知.*芳療.*入門班.*台灣芳療協會.*http://line.me/.*",
            ".*黑松.*lihi2.com/.*",
            ".*詹浩.*https://lin.ee/.*",
            ".*詹浩.*https://lin.ee/.*報名.*https://forms.gle/.*",
            ".*詹浩.*https://reurl.cc/.*",
            ".*誠好徒手保健中心.*官方.*https://lin.ee/.*",
            ".*農業局.*問卷連結.*https://reurl.cc/.*",
            ".*壽星超驚喜好禮獻給你.*生日禮今日宅配寄出.*壽星限定促銷套組.*0800076868.*bit.ly/.*",
            ".*睡覺機太赫茲熱灸墊.*體驗活動.*https://forms.gle/.*",
            ".*輔.*大.*https://reurl.cc/.*輔大.*歡迎您.*",
            ".*輔.*大.*系.*https://line.me/.*輔大.*歡迎您.*",
            ".*儀器故障-資料沒回傳",
            ".*蝦皮.*黑貓.*silia.shop.*",
            ".*衛采BB.*折價券發放通知.*結帳輸.*立即折.*reurl.cc/.*",
            ".*衛采購物.*立即把握.*優惠.*reurl.cc/.*",
            ".*學年度.*輔大.*新生輔導日與家長日事宜.*https://reurl.cc/.*輔大生科系歡迎您.*",
            ".*戰界.*澤諾尼亞.*https://bit.ly/.*",
            ".*澳洲.*講座.*https://bit.ly/.*",
            ".*澳洲.*講座.*https://reurl.cc/.*",
            ".*澳洲打工度假講座即將開始.*https://bit.ly/.*線上講座連結.*",
            ".*澳洲交換生心得分享.*線上講座連結.*https://reurl.cc/.*",
            ".*優行UC.*lihi2.com/.*",
            ".*禪心集團.*填寫問卷.*https://docs.google.com/.*",
            "【.*邀約】您好，我是PoBi波比保母.*有榮幸提供您專業.*https://abab.one/.*",
            "【中獎通知│華人婚博會】.*請按此回覆.*https://lin.ee/.*",
            "【心成能量學 上課提醒】.*https://maps.app.goo.gl/.*",
            "【心成能量學】.*https://line.me/.*",
            "【心成能量學】.*https://pse.is/.*",
            "【父親節快樂】.*輸入代碼『父親節優惠』.*https://lihi.cc/.*",
            "【活動倒數一天】.*reurl.cc/.*",
            "【退費通知】.*通知您的退費申請已處理完畢.*如有任何疑問客服.*https://lin.ee/.*",
            "【眾點運動報名網】.*請於.*前繳費.*詳情https://fsurl.tw/.*",
            "95會館<新秀321.9.11.85>誠摯邀你來體驗~憑簡訊限折200元期限至8/31止+賴app76999看照或洽0966813347",
            "Active.*Backup.*for.*Business.*was.*unsuccessful",
            "BB會員獨享好康.*立即前往秘密專區.*reurl.cc/.*",
            "Database.*not available.ERROR:.*: TNS:.*",
            "Etalking Kids課程顧問Connie官方LINE.*https://lin.ee/.*",
            "ez訂會員中心.*請點擊網址驗證.*小時內失效.*https://maac.io/.*",
            "https://tapbee.com/.*美超微.*",
            "PoBi.*波比保母.*寶寶日誌大禮包序號.*兌換詳情.*https://pobi.com.tw/.*",
            "Therewiswonewlogwcontainingwthewkeywordw.*wonw.*",
            "ThewUPSwdevicewconnectedwtowLiber.*whaswenteredwbatterywmode.wEstimatedwbatterywtime:w.*wminutes.",
            "VSL#3.*祝你生日快樂.*壽星獨享.*前結帳輸入.*reurl.cc/.*",
            "WinWin帳戶啟動號碼.*請於.*分鐘內點擊連結自動帶入.*https://tinyurl.com/.*",
            "一路發.*https://ibb.co/.*",
            "一路發.*https://www.purpleculture.net/.*",
            "一路發.*https://zh-tw.imgbb.com/.*",
            "反毒講師培訓.*場來.*醫師親自主講.*報名連結.*https://forms.gle/.*",
            "全適能健康促進暨全人照護協會.*課程.*https://reurl.cc/.*",
            "全適能健康促進暨全人照護協會.*課程.*上課時間.*地點.*https://line.me/.*",
            "好菌家.*Line官方帳號.*lin.ee/.*",
            "恭喜你成為.*https://bit.ly.*心成.*",
            "您好.*已收到您的訂單煩請傳簡訊至.*告知.*確認訂購.*我們才會寄出商品.*",
            "您好.*班群組.*https://line.me/.*",
            "您好.*提醒您有一堂免費體驗課程尚未使用.*預約體驗https://reurl.cc/.*",
            "您好.*課程群組.*https://line.me/ti/g/.*",
            "您好，這是.*課程群組.*https://line.me/.*邀請您加入，謝謝。",
            "您的.*已送達.*請盡快去取貨.* https://kf6.me/.*",
            "閣下訂購.*www.host886.com",
            "賣家提醒.*您訂購的.*已送達.*https://kf6.me/.*",
            "學員好.*原定.*課程.*依據.*發布停止上班.*補課時間.*另行通知.*",
            "繳費代碼.*金額.*客服https://lin.ee/.*"
    );
    
    
}
