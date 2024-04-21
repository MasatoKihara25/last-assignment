package com.example.lures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LureServiceTest {
    @InjectMocks
    LureService lureService;
    @Mock
    LureMapper lureMapper;

    @Test
    public void 存在するルアーを全件取得すること() {
        List<Lure> lureList = List.of(
                new Lure(1, "Balaam300", "Madness", 300, 168),
                new Lure(2, "JointedCRAW178", "GANCRAFT", 178, 56));
        doReturn(lureList).when(lureMapper).findAll();
        List<Lure> actual = lureService.findLure((String) null);
        assertThat(actual).isEqualTo(lureList);
        verify(lureMapper, times(1)).findAll();
    }

    @Test
    public void 引数に指定した文字列を含むルアーの情報が返されること() {
        List<Lure> expectedLure = List.of(new Lure(1, "Balaam300", "Madness", 300, 168));
        doReturn(expectedLure).when(lureMapper).findLures("B");
        List<Lure> actual = lureService.findLure("B");
        assertThat(actual).isEqualTo(expectedLure);
        verify(lureMapper, times(1)).findLures("B");
    }

    @Test
    public void 存在するルアーのIDを指定した場合に該当するルアーが返されること() {
        doReturn(Optional.of(new Lure(1, "Balaam300", "Madness", 300, 168))).when(lureMapper).findById(1);
        Lure actual = lureService.findLure(1);
        assertThat(actual).isEqualTo(new Lure(1, "Balaam300", "Madness", 300, 168));
        verify(lureMapper, times(1)).findById(1);
    }

    @Test
    public void 存在しないルアーのIDを指定した場合にエラーが返されること() {
        doReturn(Optional.empty()).when(lureMapper).findById(99);
        assertThatThrownBy(() -> {
            lureService.findLure(99);
        }).isInstanceOf(LureNotFoundException.class);
        verify(lureMapper, times(1)).findById(99);
    }

    @Test
    public void 新しいルアーを登録できること() {

        doReturn(Optional.empty()).when(lureMapper).findByLure("Balaam300");
        Lure actual = lureService.insert("Balaam300", "Madness", 300, 168);

        assertNotNull(actual);
        assertThat(actual).isEqualTo(new Lure("Balaam300", "Madness", 300, 168));
        verify(lureMapper, times(1)).insert(any(Lure.class));
    }

    @Test
    public void 新しいルアーを登録する時に製品名の重複がある場合は例外をスローすること() {

        doReturn(Optional.of(new Lure("Balaam300", "Madness", 300, 168))).when(lureMapper).findByLure("Balaam300");

        assertThatThrownBy(() -> {
            lureService.insert("Balaam300", "Madness", 300, 168);
        }).isInstanceOf(LureDuplicatedException.class);
        verify(lureMapper, times(1)).findByLure("Balaam300");
    }

    @Test
    public void 既存のルアーを更新できること() {

        Lure lure = new Lure(1, "Balaam300", "Madness", 300, 168);
        doReturn(Optional.of(lure)).when(lureMapper).findById(1);
        doReturn(Optional.empty()).when(lureMapper).findByLure("Balaam300");

        Lure actual = lureService.update(1, "Balaam300", "Madness", 300, 168);

        assertNotNull(actual);
        assertThat(actual).isEqualTo(new Lure(1, "Balaam300", "Madness", 300, 168));
        verify(lureMapper).update(lure);
    }

    @Test
    public void ルアーを更新する時に指定したIDが見つからない場合は例外をスローすること() {

        doReturn(Optional.empty()).when(lureMapper).findById(99);

        assertThatThrownBy(() -> {
            lureService.update(99, "Balaam300", "Madness", 300, 168);
        }).isInstanceOf(LureNotFoundException.class);
    }

    @Test
    public void ルアーを更新する時に製品名が重複している場合は例外をスローすること() {

        Lure updatingMusic = new Lure(1, "Balaam300", "Madness", 300, 168);
        doReturn(Optional.of(updatingMusic)).when(lureMapper).findById(1);

        Lure duplicatedLure = new Lure(2, "Balaam300", "Madness", 300, 168);
        doReturn(Optional.of(duplicatedLure)).when(lureMapper).findByLure("Balaam300");

        assertThatThrownBy(() -> {
            lureService.update(1, "Balaam300", "Madness", 300, 168);
        }).isInstanceOf(LureDuplicatedException.class);
    }

    @Test
    public void IDを指定してルアーを削除できること() {
        Lure lure = new Lure(1, "Balaam300", "Madness", 300, 168);
        doReturn(Optional.of(lure)).when(lureMapper).findById(1);

        Lure actual = lureService.delete(1);

        assertThat(actual).isEqualTo(new Lure(1, "Balaam300", "Madness", 300, 168));
    }

}
