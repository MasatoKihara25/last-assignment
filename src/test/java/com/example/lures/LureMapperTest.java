package com.example.lures;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LureMapperTest {

    @Autowired
    LureMapper lureMapper;

    @Test
    @DataSet(value = "datasets/lures.yml")
    @Transactional
    public void すべてのルアーデータが取得できること() {
        List<Lure> lureList = lureMapper.findAll();

        assertThat(lureList)
                .hasSize(4)
                .contains(
                        new Lure(1, "Balaam300", "Madness", 300, 168),
                        new Lure(2, "JointedCRAW178", "GANCRAFT", 178, 56),
                        new Lure(3, "SILENTKILLER175", "deps", 175, 70),
                        new Lure(4, "BULLSHOOTER160", "deps", 160, 98)
                );
    }

    @Test
    @DataSet(value = "datasets/lures.yml")
    @Transactional
    public void 引数に指定した文字列を含むルアーデータが返されること() {
        List<Lure> lureList = lureMapper.findLures("join");

        assertThat(lureList)
                .hasSize(1)
                .contains(
                        new Lure(2, "JointedCRAW178", "GANCRAFT", 178, 56)
                );
    }

    @Test
    @DataSet(value = "datasets/lures.yml")
    @Transactional
    public void 存在するIDを指定した場合に該当するルアーデータが返されること() {
        Optional<Lure> lureList = lureMapper.findById(1);

        assertThat(lureList)
                .isPresent()
                .hasValue(
                        new Lure(1, "Balaam300", "Madness", 300, 168)
                );
    }

    @Test
    @DataSet(value = "datasets/lures.yml")
    @Transactional
    public void 存在しないIDを指定した場合に空のデータが返されること() {
        Optional<Lure> lureList = lureMapper.findById(5);

        assertThat(lureList)
                .isEmpty();
    }

    @Test
    @ExpectedDataSet(value = "datasets/insertLures.yml", ignoreCols = "id")
    @Transactional
    public void 新しいルアーデータを登録できること() {
        Lure lure = new Lure("Royal flash", "EVERGREEN", 160, 60);
        lureMapper.insert(lure);

        Optional<Lure> musicList = lureMapper.findById(lure.getId());
        assertThat(musicList).isPresent();
        assertThat(musicList.get().getProduct()).isEqualTo("Royal flash");
        assertThat(musicList.get().getCompany()).isEqualTo("EVERGREEN");
        assertThat(musicList.get().getSize()).isEqualTo(160);
        assertThat(musicList.get().getWeight()).isEqualTo(60);
    }

    @Test
    @DataSet(value = "datasets/lures.yml")
    @ExpectedDataSet(value = "datasets/updatedLures.yml", ignoreCols = "id")
    @Transactional
    public void IDを指定してルアーデータを更新できること() {

        Lure lure = new Lure(3, "SLIDESWIMMER175", "deps", 175, 72.8);
        lureMapper.update(lure);

        Optional<Lure> updatedLure = lureMapper.findById(3);
        assertThat(updatedLure).isPresent();
        assertThat(updatedLure.get().getProduct()).isEqualTo("SLIDESWIMMER175");
        assertThat(updatedLure.get().getCompany()).isEqualTo("deps");
        assertThat(updatedLure.get().getSize()).isEqualTo(175);
        assertThat(updatedLure.get().getWeight()).isEqualTo(72.8);
    }

    @Test
    @DataSet(value = "datasets/lures.yml")
    @ExpectedDataSet(value = "datasets/deletedLures.yml", ignoreCols = "id")
    @Transactional
    public void IDを指定してルアーデータを削除できること() {
        Lure lure = new Lure(4, "BULLSHOOTER160", "deps", 160, 98);
        lureMapper.delete(lure);

        Optional<Lure> deletedLure = lureMapper.findById(4);
        assertThat(deletedLure).isEmpty();
    }
}
