package sample.utils;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

/** ページネーション */
@Data
@Component
public class Pagination<T> {
    /** ページネーションのデータ */
    private List<T> data;

    /** 総ページサイズ */
    private int totalPage;

    /** 総件数 */
    private long totalCount;

    /**
     * ページングの設定
     * 
     * @param result     ページング対象のデータ
     * @param totalCount 総件数
     * @param pageSize   ページサイズ
     * @return ページネーション情報
     */
    public Pagination<T> paging(List<T> result, long totalCount, int pageSize) {
        this.data = result;
        this.totalCount = totalCount;
        if (pageSize == 0) {
            this.totalPage = 0;
            return this;
        }
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);

        return this;
    }

    /**
     * ページング開始位置の計算
     * 
     * @param pageSize   ページサイズ
     * @param pageNumber ページ番号
     * @return 開始位置
     */
    public static Integer pageNumberConvert(int pageSize, int pageNumber) {
        return (pageNumber - 1) * pageSize;
    }

}
