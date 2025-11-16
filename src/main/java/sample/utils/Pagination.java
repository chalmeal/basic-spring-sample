package sample.utils;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * <pre>
 * ページネーションのコンテキストクラス
 * ページネーションに関する情報を保持します。
 * </pre>
 */
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
     * <pre>
     * ページングの設定
     * データと総件数からページング情報を設定します。
     * </pre>
     * 
     * @param result     ページング対象のデータ
     * @param totalCount 総件数
     * @return ページング情報
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

}
