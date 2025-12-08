package sample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sample.service.SystemService;

/** システムコントローラー */
@RestController
@RequestMapping("/api/system")
@RequiredArgsConstructor
public class SystemController {
    /** システムサービスDI */
    private final SystemService systemService;

    /**
     * ヘルスチェック
     * 
     * @return 疎通確認結果
     */
    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        systemService.healthCheck();
        return ResponseEntity.ok().build();
    }

}
