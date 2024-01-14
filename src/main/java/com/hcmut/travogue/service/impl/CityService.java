package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.file.CloudinaryService;
import com.hcmut.travogue.model.dto.Response.PageResponse;
import com.hcmut.travogue.model.entity.TravelActivity.City;
import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import com.hcmut.travogue.repository.TravelActivity.CityRepository;
import com.hcmut.travogue.repository.TravelActivity.TravelActivityRepository;
import com.hcmut.travogue.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CityService implements ICityService {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TravelActivityRepository travelActivityRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public List<City> getPopularCities() {
        return cityRepository.findFirst10ByOrderByTravelPointDesc();
    }

    @Override
    public List<City> getCities() {
        return cityRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public PageResponse<City> searchCities(int pageNumber, int pageSize, String sortField, String criteria) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField).ascending());

        return new PageResponse<>(cityRepository.findPageCities(criteria, pageable));
    }

    @Override
    public PageResponse<TravelActivity> getTravelActivitiesByCity(UUID cityId, String keyword, int pageNumber, int pageSize, String sortField) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField).descending());
        return new PageResponse<>(travelActivityRepository.findPageTravelActivitiesByCity(cityId, keyword, pageable));
    }

    @Override
    public City uploadMainImage(UUID cityId, MultipartFile image) throws IOException {
        City city = cityRepository.findById(cityId).orElseThrow();

        city.setImages(cloudinaryService.uploadFile("city", image));
        return cityRepository.save(city);
    }

    @Override
    public void dumpData() {
        List<City> cities = new ArrayList<>();

        City haGiang = City.builder()
                .name("Hà Giang")
                .destinations("Hà Giang;Cao Nguyên Đồng Văn;Đèo Mã Pí Lèng;Cột Cờ Lũng Cú;Làng văn hóa các dân tộc")
                .description("Hà Giang là một tỉnh miền núi cao ở phía bắc Việt Nam, giáp biên giới với Trung Quốc. Tỉnh được biết đến với những ngọn núi hùng vĩ, những bản làng dân tộc thiểu số và những cảnh quan thiên nhiên tuyệt đẹp.")
                .cityTags("hoang sơ;rừng núi;thiên nhiên;lịch sử;bản làng")
                .country("Việt Nam")
                .travelPoint(9.0)
                .build();
        cities.add(haGiang);

        City hanoi = City.builder()
                .name("Hà Nội")
                .destinations("Hồ Gươm;Phố cổ;Chùa Một Cột;Văn Miếu - Quốc Tử Giám;Lăng Bác")
                .description("Hà Nội là thủ đô của Việt Nam, là trung tâm chính trị, văn hóa, kinh tế và giáo dục của đất nước. Thành phố có nhiều di sản văn hóa quan trọng, các khu phố phồn hoa và nhiều địa điểm du lịch nổi tiếng.")
                .cityTags("thủ đô;phố cổ;di sản văn hóa;di tích lịch sử")
                .country("Việt Nam")
                .travelPoint(9.5)
                .build();
        cities.add(hanoi);


// Ho Chi Minh City
        City hcmCity = City.builder()
                .name("Thành phố Hồ Chí Minh")
                .destinations("Sài Gòn;Chợ Bến Thành;Nhà hát Thành phố;Công viên 30/4;Phố đi bộ Nguyễn Huệ")
                .description("Thành phố Hồ Chí Minh, trước đây được gọi là Sài Gòn, là thành phố lớn nhất và là trung tâm kinh tế của Việt Nam. Thành phố có nhiều điểm tham quan, các khu vui chơi, nhà hàng và công viên nổi tiếng.")
                .cityTags("kinh tế;vui chơi;thành phố lớn;di tích lịch sử")
                .country("Việt Nam")
                .travelPoint(9.2)
                .build();
        cities.add(hcmCity);

        // Hải Phòng
        City haiPhong = City.builder()
                .name("Hải Phòng")
                .destinations("Cầu Rồng;Chợ Sắt;Bảo tàng Hải Phòng;Bãi biển Đồ Sơn;Công viên Đại Dương")
                .description("Hải Phòng là thành phố cảng lớn thứ hai của Việt Nam, nằm ở cửa sông Cấm. Thành phố có nhiều di tích lịch sử, danh lam thắng cảnh và các khu vui chơi giải trí hấp dẫn.")
                .cityTags("cảng biển;địa danh lịch sử;tâm linh;vui chơi")
                .country("Việt Nam")
                .travelPoint(8.8)
                .build();
        cities.add(haiPhong);

// Đà Nẵng
        City daNang = City.builder()
                .name("Đà Nẵng")
                .destinations("Biển Mỹ Khê;Bãi biển Non Nước;Cầu Rồng;Bảo tàng Chăm Đà Nẵng;Đà Nẵng Tower")
                .description("Đà Nẵng là thành phố du lịch nổi tiếng của Việt Nam, nằm ở trung tâm miền Trung. Thành phố có nhiều bãi biển đẹp, các di tích lịch sử và các danh lam thắng cảnh ấn tượng.")
                .cityTags("du lịch;biển;địa danh lịch sử;tâm linh")
                .country("Việt Nam")
                .travelPoint(9.2)
                .build();
        cities.add(daNang);

// Nha Trang
        City nhaTrang = City.builder()
                .name("Nha Trang")
                .destinations("Biển Nha Trang;Vịnh Nha Trang;Bảo tàng Khánh Hòa;Chùa Long Sơn;Tháp Bà Ponagar")
                .description("Nha Trang là thành phố biển nổi tiếng của Việt Nam, nằm ở miền Trung. Thành phố có nhiều bãi biển đẹp, các vịnh biển thơ mộng và các di tích lịch sử, danh lam thắng cảnh độc đáo.")
                .cityTags("du lịch;biển;địa danh lịch sử;tâm linh")
                .country("Việt Nam")
                .travelPoint(9.4)
                .build();
        cities.add(nhaTrang);

// Phú Quốc
        City phuQuoc = City.builder()
                .name("Phú Quốc")
                .destinations("Bãi Sao;Bãi Dài;Vườn Quốc gia Phú Quốc;Bãi Kem;Suối Tranh")
                .description("Phú Quốc là hòn đảo lớn nhất của Việt Nam, nằm ở miền Nam. Hòn đảo có nhiều bãi biển đẹp, các khu rừng nguyên sinh và các di tích lịch sử, danh lam thắng cảnh hấp dẫn.")
                .cityTags("du lịch;biển;địa danh lịch sử;tâm linh")
                .country("Việt Nam")
                .travelPoint(9.6)
                .build();
        cities.add(phuQuoc);

// Huế
        City hue = City.builder()
                .name("Huế")
                .destinations("Đại Nội Huế;Cung An Định;Hồ Tịnh Tâm;Chùa Thiên Mụ;Lăng Tự Đức")
                .description("Huế là cố đô của Việt Nam, nằm ở miền Trung. Thành phố có nhiều di tích lịch sử, danh lam thắng cảnh và các khu vui chơi giải trí hấpdẫn.")
                .cityTags("cổ tích;di sản văn hóa;di tích lịch sử")
                .country("Việt Nam")
                .travelPoint(9.0)
                .build();
        cities.add(hue);

// Đà Lạt
        City daLat = City.builder()
                .name("Đà Lạt")
                .destinations("Thác Datanla;Vườn hoa Đà Lạt;Bảo tàng Lâm Đồng;Chùa Linh Phước;Nhà thờ Con Gà")
                .description("Đà Lạt là thành phố du lịch nổi tiếng của Việt Nam, nằm ở miền Trung. Thành phố có khí hậu mát mẻ quanh năm, nhiều cảnh quan thiên nhiên tươi đẹp và các di tích lịch sử, danh lam thắng cảnh độc đáo.")
                .cityTags("du lịch;thiên nhiên;tâm linh")
                .country("Việt Nam")
                .travelPoint(9.3)
                .build();
        cities.add(daLat);

// Buôn Ma Thuột
        City buonMaThuot = City.builder()
                .name("Buôn Ma Thuột")
                .destinations("Biển Hồ;Chùa Khải Đoan;Bảo tàng Đắk Lắk;Công viên Yohimbe;Chợ Ea Tam")
                .description("Buôn Ma Thuột là thủ phủ của tỉnh Đắk Lắk, nằm ở miền Trung. Thành phố có nhiều di tích lịch sử, danh lam thắng cảnh và các khu vui chơi giải trí hấp dẫn.")
                .cityTags("tâm linh;du lịch;biển")
                .country("Việt Nam")
                .travelPoint(8.6)
                .build();
        cities.add(buonMaThuot);

        // Nghệ An
        City ngheAn = City.builder()
                .name("Nghệ An")
                .destinations("Cửa Lò;Phố cổ Vinh;Bãi biển Cửa Lò;Khu du lịch sinh thái Quỳnh Lưu;Quần thể di tích Cố đô Hoa Lư")
                .description("Nghệ An là tỉnh có diện tích lớn nhất miền Trung Việt Nam, là quê hương của nhiều danh nhân lịch sử, văn hóa. Tỉnh có nhiều danh lam thắng cảnh nổi tiếng, bao gồm bãi biển Cửa Lò, một trong những bãi biển đẹp nhất Việt Nam.")
                .cityTags("du lịch;biển;lịch sử")
                .country("Việt Nam")
                .travelPoint(8.8)
                .build();
        cities.add(ngheAn);

// Hà Tĩnh
        City haTinh = City.builder()
                .name("Hà Tĩnh")
                .destinations("Cửa Sót;Phố cổ Hà Tĩnh;Núi Hồng Lĩnh;Làng Sen;Đền Chiêu Trưng")
                .description("Hà Tĩnh là tỉnh ven biển thuộc vùng Bắc Trung Bộ Việt Nam, là quê hương của Chủ tịch Hồ Chí Minh. Tỉnh có nhiều danh lam thắng cảnh nổi tiếng, bao gồm núi Hồng Lĩnh, một trong những ngọn núi đẹp nhất Việt Nam.")
                .cityTags("du lịch;biển;lịch sử")
                .country("Việt Nam")
                .travelPoint(8.6)
                .build();
        cities.add(haTinh);

// Quảng Bình
        City quangBinh = City.builder()
                .name("Quảng Bình")
                .destinations("Vườn quốc gia Phong Nha - Kẻ Bàng;Động Phong Nha;Động Thiên Đường;Sông Son;Đèo Ngang")
                .description("Quảng Bình là tỉnh ven biển thuộc vùng Bắc Trung Bộ Việt Nam, là nơi có nhiều danh lam thắng cảnh nổi tiếng, bao gồm Vườn quốc gia Phong Nha - Kẻ Bàng, một trong những khu vực có hệ thống hang động đá vôi lớn nhất thế giới.")
                .cityTags("du lịch;hang động;thiên nhiên")
                .country("Việt Nam")
                .travelPoint(9.1)
                .build();
        cities.add(quangBinh);

// Quảng Trị
        City quangTri = City.builder()
                .name("Quảng Trị")
                .destinations("Thành Cổ Quảng Trị;Cầu Hiền Lương;Thành cổ La Vang;Bãi biển Cửa Tùng;Đèo Ngang")
                .description("Quảng Trị là tỉnh ven biển thuộc vùng Bắc Trung Bộ Việt Nam, là nơi đã diễn ra nhiều trận đánh ác liệt trong cuộc kháng chiến chống Mỹ. Tỉnh có nhiều di tích lịch sử nổi tiếng, bao gồm Thành Cổ Quảng Trị, một trong những di tích lịch sử quan trọng nhất của Việt Nam.")
                .cityTags("lịch sử;du lịch;chiến tranh")
                .country("Việt Nam")
                .travelPoint(8.7)
                .build();
        cities.add(quangTri);

        City anGiang = City.builder()
                .name("An Giang")
                .destinations("An Giang;Châu Đốc;Long Xuyên;Tịnh Biên;Tri Tôn")
                .description("An Giang là một tỉnh thuộc vùng đồng bằng sông Cửu Long, Việt Nam. Tỉnh được biết đến với những cảnh quan thiên nhiên tươi đẹp, có sông nước mênh mông, có núi non kỳ vĩ, có rừng tràm, có đồng ruộng bát ngát, và có nhiều di tích lịch sử văn hóa.")
                .cityTags("sông nước;rừng tràm;đồng quê;lịch sử;văn hóa")
                .country("Việt Nam")
                .travelPoint(8.5)
                .build();
        cities.add(anGiang);

        City baRiaVungTau = City.builder()
                .name("Bà Rịa – Vũng Tàu")
                .destinations("Bà Rịa – Vũng Tàu;Vũng Tàu;Bà Rịa;Núi Lớn;Núi Nhỏ")
                .description("Bà Rịa – Vũng Tàu là một tỉnh ven biển thuộc vùng Đông Nam Bộ, Việt Nam. Tỉnh được biết đến với những bãi biển đẹp, những di tích lịch sử văn hóa, và những khu du lịch sinh thái.")
                .cityTags("biển;di tích;suối khoáng nóng;sinh thái;hòn đảo")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(baRiaVungTau);

        City bacGiang = City.builder()
                .name("Bắc Giang")
                .destinations("Bắc Giang;Thành phố Bắc Giang;Lục Nam;Đông Anh;Sơn Động")
                .description("Bắc Giang là một tỉnh thuộc vùng Đông Bắc Bộ, Việt Nam. Tỉnh được biết đến với những cảnh quan thiên nhiên tươi đẹp, có núi non hùng vĩ, có rừng nguyên sinh, có đồng ruộng bát ngát, và có nhiều di tích lịch sử văn hóa.")
                .cityTags("núi non;rừng nguyên sinh;đồng quê;lịch sử;văn hóa")
                .country("Việt Nam")
                .travelPoint(7.5)
                .build();
        cities.add(bacGiang);

        City bacKan = City.builder()
                .name("Bắc Kạn")
                .destinations("Bắc Kạn;Thành phố Bắc Kạn;Bạch Thông;Chợ Mới;Na Rì")
                .description("Bắc Kạn là một tỉnh thuộc vùng Đông Bắc Bộ, Việt Nam. Tỉnh được biết đến với những cảnh quan thiên nhiên tươi đẹp, có núi non hùng vĩ, có rừng nguyên sinh, có đồng ruộng bát ngát, và có nhiều di tích lịch sử văn hóa.")
                .cityTags("núi non;rừng nguyên sinh;động vật hoang dã;lịch sử;văn hóa")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(bacKan);

        City bacLieu = City.builder()
                .name("Bạc Liêu")
                .destinations("Bạc Liêu;Thành phố Bạc Liêu;Cù Lao Dung;Giao Hòa; Vĩnh Lợi")
                .description("Bạc Liêu là một tỉnh thuộc vùng đồng bằng sông Cửu Long, Việt Nam. Tỉnh được biết đến với những cảnh quan thiên nhiên tươi đẹp, có sông nước mênh mông, có rừng tràm, có đồng ruộng bát ngát, và có nhiều di tích lịch sử văn hóa.")
                .cityTags("sông nước;rừng tràm;đồng quê;lịch sử;văn hóa")
                .country("Việt Nam")
                .travelPoint(7.5)
                .build();
        cities.add(bacLieu);

        City bacNinh = City.builder()
                .name("Bắc Ninh")
                .destinations("Bắc Ninh;Thành phố Bắc Ninh;Lục Nam;Đông Anh;Sơn Động")
                .description("Bắc Ninh là một tỉnh thuộc vùng Đông Bắc Bộ, Việt Nam. Tỉnh được biết đến với những cảnh quan thiên nhiên tươi đẹp, có núi non hùng vĩ, có rừng nguyên sinh, có đồng ruộng bát ngát, và có nhiều di tích lịch sử văn hóa.")
                .cityTags("núi non;rừng nguyên sinh;đồng quê;lịch sử;văn hóa")
                .country("Việt Nam")
                .travelPoint(7.5)
                .build();
        cities.add(bacNinh);

        City benTre = City.builder()
                .name("Bến Tre")
                .destinations("Bến Tre;Thành phố Bến Tre;Ba Tri;Mỏ Cày Nam;Mỏ Cày Bắc")
                .description("Bến Tre là một tỉnh thuộc vùng đồng bằng sông Cửu Long, Việt Nam. Tỉnh được biết đến với những vườn dừa bạt ngàn, có sông nước mênh mông, có đồng ruộng bát ngát, và có nhiều di tích lịch sử văn hóa.")
                .cityTags("sông nước;dừa;đồng quê;lịch sử;văn hóa")
                .country("Việt Nam")
                .travelPoint(7.0)
                .build();
        cities.add(benTre);

        City binhDinh = City.builder()
                .name("Bình Định")
                .destinations("Bình Định;Thành phố Quy Nhơn;Phù Cát;Hải Giang;Đảo Cù Lao Xanh")
                .description("Bình Định là một tỉnh ven biển nằm ở phía bắc khu vực duyên hải Nam Trung Bộ, Việt Nam. Tỉnh được biết đến với những cảnh quan thiên nhiên tươi đẹp, có biển xanh, cát trắng, nắng vàng, có núi non hùng vĩ, có đồng ruộng bát ngát, và có nhiều di tích lịch sử văn hóa.")
                .cityTags("biển;núi;đồng quê;lịch sử;văn hóa")
                .country("Việt Nam")
                .travelPoint(7.5)
                .build();
        cities.add(binhDinh);

        City binhDuong = City.builder()
                .name("Bình Dương")
                .destinations("Bình Dương;Thành phố Thủ Dầu Một;Thủ Dầu Một;Dĩ An; Thuận An")
                .description("Bình Dương là một tỉnh thuộc vùng Đông Nam Bộ, Việt Nam. Tỉnh được biết đến với những khu công nghiệp lớn, là một trung tâm kinh tế - công nghiệp quan trọng của cả nước. Ngoài ra, Bình Dương còn có nhiều cảnh quan thiên nhiên tươi đẹp, có núi non, có sông nước, có đồng ruộng bát ngát, và có nhiều di tích lịch sử văn hóa.")
                .cityTags("công nghiệp;núi;sông nước;đồng quê;lịch sử;văn hóa")
                .country("Việt Nam")
                .travelPoint(7.0)
                .build();
        cities.add(binhDuong);

        City binhPhuoc = City.builder()
                .name("Bình Phước")
                .destinations("Bình Phước;Thành phố Đồng Xoài;Đồng Xoài;Đồng Phú;Phước Long")
                .description("Bình Phước là một tỉnh thuộc vùng Đông Nam Bộ, Việt Nam. Tỉnh được biết đến với những cảnh quan thiên nhiên tươi đẹp, có núi non, có sông nước, có đồng ruộng bát ngát, và có nhiều di tích lịch sử văn hóa.")
                .cityTags("núi non;sông nước;đồng quê;lịch sử;văn hóa")
                .country("Việt Nam")
                .travelPoint(7.5)
                .build();
        cities.add(binhPhuoc);

        City binhThuan = City.builder()
                .name("Bình Thuận")
                .destinations("Bình Thuận;Thành phố Phan Thiết;Phan Thiết;La Gi;Mũi Né")
                .description("Bình Thuận là một tỉnh thuộc vùng duyên hải Nam Trung Bộ, Việt Nam. Tỉnh được biết đến với những bãi biển đẹp, có nắng vàng, cát trắng, nước trong xanh, là một trong những điểm du lịch biển nổi tiếng nhất Việt Nam.")
                .cityTags("biển;đồng quê;lịch sử;văn hóa")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(binhThuan);

        City caMau = City.builder()
                .name("Cà Mau")
                .destinations("Cà Mau;Thành phố Cà Mau;Kiên Lương;U Minh;Đất Mũi")
                .description("Cà Mau là một tỉnh thuộc vùng đồng bằng sông Cửu Long, Việt Nam. Tỉnh được biết đến với những cánh rừng ngập mặn bạt ngàn, có hệ sinh thái đa dạng, phong phú, là một trong những điểm du lịch sinh thái nổi tiếng nhất Việt Nam.")
                .cityTags("rừng ngập mặn;đồng quê;lịch sử;văn hóa")
                .country("Việt Nam")
                .travelPoint(7.5)
                .build();
        cities.add(caMau);

        City canTho = City.builder()
                .name("Cần Thơ")
                .destinations("Cần Thơ;Thành phố Cần Thơ;Cồn Sơn;Bến Ninh Kiều;Bảo tàng Cần Thơ")
                .description("Cần Thơ là một thành phố thuộc vùng đồng bằng sông Cửu Long, Việt Nam. Thành phố được biết đến với những khu chợ nổi sầm uất, có cảnh quan thiên nhiên tươi đẹp, và có nhiều di tích lịch sử văn hóa.")
                .cityTags("chợ nổi;đồng quê;lịch sử;văn hóa")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(canTho);

        City caoBang = City.builder()
                .name("Cao Bằng")
                .destinations("Cao Bằng;Thành phố Cao Bằng;Đồng Đăng;Bảo tàng tỉnh Cao Bằng;Công viên Địa chất Cao Bằng")
                .description("Cao Bằng là một tỉnh thuộc vùng Đông Bắc Bộ, Việt Nam. Tỉnh được biết đến với những cảnh quan thiên nhiên hùng vĩ, có nhiều di tích lịch sử văn hóa, và là nơi sinh của Chủ tịch Hồ Chí Minh.")
                .cityTags("núi non;lịch sử;văn hóa")
                .country("Việt Nam")
                .travelPoint(8.5)
                .build();
        cities.add(caoBang);

        City dakLak = City.builder()
                .name("Đắk Lắk")
                .destinations("Đắk Lắk;Buôn Ma Thuột;Buôn Đôn;Hồ Lắk;Vườn quốc gia Yok Đôn")
                .description("Đắk Lắk là một tỉnh thuộc vùng Tây Nguyên, Việt Nam. Tỉnh được biết đến với những cánh rừng đại ngàn, có nhiều di tích lịch sử văn hóa của người dân tộc thiểu số, và là nơi sản xuất cà phê Robusta lớn nhất Việt Nam.")
                .cityTags("rừng;núi non;bản làng;văn hóa")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(dakLak);

        City dakNong = City.builder()
                .name("Đắk Nông")
                .destinations("Đắk Nông;Gia Nghĩa;Cầu treo Buôn Jun;Công viên địa chất Đắk Nông;Hồ Ea Kao")
                .description("Đắk Nông là một tỉnh thuộc vùng Tây Nguyên, Việt Nam. Tỉnh được biết đến với những cánh rừng cao su bạt ngàn, có nhiều di tích lịch sử văn hóa của người dân tộc thiểu số, và là nơi sản xuất cà phê Robusta lớn thứ hai Việt Nam.")
                .cityTags("rừng;núi non;bản làng;văn hóa")
                .country("Việt Nam")
                .travelPoint(7.5)
                .build();
        cities.add(dakNong);

        City dienBien = City.builder()
                .name("Điện Biên")
                .destinations("Điện Biên;Thành phố Điện Biên Phủ;Điện Biên Phủ;Cầu Mường Thanh;Bảo tàng Điện Biên Phủ")
                .description("Điện Biên là một tỉnh thuộc vùng Tây Bắc Bộ, Việt Nam. Tỉnh được biết đến với chiến thắng Điện Biên Phủ lừng lẫy năm châu, chấn động địa cầu, có nhiều di tích lịch sử văn hóa, và là điểm đến du lịch nổi tiếng của Việt Nam.")
                .cityTags("lịch sử;văn hóa;núi non")
                .country("Việt Nam")
                .travelPoint(8.5)
                .build();
        cities.add(dienBien);

        City dongNai = City.builder()
                .name("Đồng Nai")
                .destinations("Đồng Nai;Biên Hòa;Long Thành;Vườn quốc gia Cát Tiên;Đồi Cù")
                .description("Đồng Nai là một tỉnh thuộc vùng Đông Nam Bộ, Việt Nam. Tỉnh được biết đến với những cánh rừng nguyên sinh, có nhiều di tích lịch sử văn hóa của người dân tộc thiểu số, và là một trong những trung tâm kinh tế - công nghiệp lớn của Việt Nam.")
                .cityTags("rừng;núi non;bản làng;kinh tế")
                .country("Việt Nam")
                .travelPoint(7.0)
                .build();
        cities.add(dongNai);

        City dongThap = City.builder()
                .name("Đồng Tháp")
                .destinations("Đồng Tháp;Cao Lãnh;Sa Đéc;Khu du lịch sinh thái Gáo Giồng;Làng hoa Sa Đéc;Cánh đồng sen Tháp Mười")
                .description("Đồng Tháp là một tỉnh thuộc vùng Đồng bằng sông Cửu Long, Việt Nam. Tỉnh được biết đến với những cánh đồng lúa bạt ngàn, có nhiều di tích lịch sử văn hóa của người dân tộc thiểu số, và là một trong những trung tâm sản xuất lúa gạo lớn nhất Việt Nam.")
                .cityTags("đồng lúa;làng hoa;sen;lịch sử;văn hóa")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(dongThap);

        City giaLai = City.builder()
                .name("Gia Lai")
                .destinations("Gia Lai;Pleiku;Biển Hồ;Núi lửa Chư Đăng Ya;Thác K50;Công viên địa chất Kon Ka Kinh")
                .description("Gia Lai là một tỉnh thuộc vùng Tây Nguyên, Việt Nam. Tỉnh được biết đến với những cánh rừng nguyên sinh bạt ngàn, có nhiều di tích lịch sử văn hóa của người dân tộc thiểu số, và là một trong những trung tâm sản xuất cà phê lớn nhất Việt Nam.")
                .cityTags("rừng;núi lửa;thác nước;cà phê;văn hóa")
                .country("Việt Nam")
                .travelPoint(8.5)
                .build();
        cities.add(giaLai);
        City haNam = City.builder()
                .name("Hà Nam")
                .destinations("Hà Nam;Phủ Lý;Chùa Tam Chúc;Làng Vũ Đại;Vườn quốc gia Tam Đảo")
                .description("Hà Nam là một tỉnh thuộc vùng Đồng bằng sông Hồng, Việt Nam. Tỉnh được biết đến với những di tích lịch sử văn hóa lâu đời, có nhiều lễ hội truyền thống đặc sắc, và là một trong những trung tâm sản xuất lúa gạo lớn nhất Việt Nam.")
                .cityTags("lịch sử;văn hóa;lễ hội;lúa gạo")
                .country("Việt Nam")
                .travelPoint(7.5)
                .build();
        cities.add(haNam);


        City haiDuong = City.builder()
                .name("Hải Dương")
                .destinations("Hải Dương;Thành phố Hải Dương;Chùa Cổ Am;Làng gốm Chu Đậu;Lễ hội chọi trâu Đồ Sơn;Biển Đồ Sơn")
                .description("Hải Dương là một tỉnh thuộc vùng đồng bằng sông Hồng, Việt Nam. Tỉnh được biết đến với những di tích lịch sử văn hóa lâu đời, có nhiều lễ hội truyền thống đặc sắc, và là một trong những trung tâm sản xuất lúa gạo lớn nhất Việt Nam.")
                .cityTags("lịch sử;văn hóa;lễ hội;đồ ăn")
                .country("Việt Nam")
                .travelPoint(7.5)
                .build();
        cities.add(haiDuong);

        City hauGiang = City.builder()
                .name("Hậu Giang")
                .destinations("Hậu Giang;Vị Thanh;Chợ nổi Ngã Bảy;Cù lao Tân Lộc;Khu du lịch sinh thái Rừng tràm Trà Sư;Đầm Thị Nại")
                .description("Hậu Giang là một tỉnh thuộc vùng Đồng bằng sông Cửu Long, Việt Nam. Tỉnh được biết đến với những di tích lịch sử văn hóa lâu đời, có nhiều lễ hội truyền thống đặc sắc, và là một trong những trung tâm sản xuất lúa gạo lớn nhất Việt Nam.")
                .cityTags("lịch sử;văn hóa;lễ hội;ẩm thực;đầm nước lợ")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(hauGiang);

        City hoaBinh = City.builder()
                .name("Hòa Bình")
                .destinations("Hòa Bình;Thành phố Hòa Bình;Thung Nai;Mai Châu;Lũng Vân;Vườn quốc gia Cúc Phương")
                .description("Hòa Bình là một tỉnh miền núi thuộc vùng Tây Bắc Bộ, Việt Nam. Tỉnh được biết đến với những danh lam thắng cảnh thiên nhiên tuyệt đẹp, những nét văn hóa đặc sắc của đồng bào các dân tộc thiểu số, và là một trong những tỉnh có nhiều tiềm năng du lịch của Việt Nam.")
                .cityTags("thiên nhiên;văn hóa;ẩm thực;động thực vật")
                .country("Việt Nam")
                .travelPoint(8.5)
                .build();
        cities.add(hoaBinh);
        City hungYen = City.builder()
                .name("Hưng Yên")
                .destinations("Hưng Yên;Thành phố Hưng Yên;Đền Mẫu Cửa Ông;Chùa Chuông;Làng gốm Chu Đậu;Làng Nhị Châu")
                .description("Hưng Yên là một tỉnh thuộc vùng đồng bằng sông Hồng, Việt Nam. Tỉnh được biết đến với những di tích lịch sử văn hóa lâu đời, có nhiều lễ hội truyền thống đặc sắc, và là một trong những trung tâm sản xuất lúa gạo lớn nhất Việt Nam.")
                .cityTags("lịch sử;văn hóa;lễ hội;ẩm thực;đồ gốm")
                .country("Việt Nam")
                .travelPoint(7.5)
                .build();
        cities.add(hungYen);
        City khanhHoa = City.builder()
                .name("Khánh Hòa")
                .destinations("Khánh Hòa;Nha Trang;Vịnh Nha Trang;Bãi biển Dốc Lết;Hòn Chồng;Hòn Đỏ;Làng chài cổ Hà Ra")
                .description("Khánh Hòa là một tỉnh duyên hải Nam Trung Bộ, Việt Nam. Tỉnh được biết đến với những danh lam thắng cảnh thiên nhiên tuyệt đẹp, những di tích lịch sử văn hóa lâu đời, và là một trong những trung tâm du lịch nổi tiếng của Việt Nam.")
                .cityTags("thiên nhiên;du lịch biển đảo;lịch sử;văn hóa")
                .country("Việt Nam")
                .travelPoint(9.0)
                .build();
        cities.add(khanhHoa);
        City kiengiang = City.builder()
                .name("Kiên Giang")
                .destinations("Kiên Giang;Rạch Giá;Phú Quốc;Chợ nổi Rạch Giá;Khu du lịch sinh thái Rừng tràm Trà Sư;Đầm Thị Nại")
                .description("Kiên Giang là một tỉnh ven biển thuộc vùng Đồng bằng sông Cửu Long, Việt Nam. Tỉnh có diện tích lớn nhất vùng Tây Nam Bộ và lớn thứ hai ở Nam Bộ. Kiên Giang được biết đến với những danh lam thắng cảnh thiên nhiên tuyệt đẹp, những di tích lịch sử văn hóa lâu đời, và là một trong những trung tâm du lịch nổi tiếng của Việt Nam.")
                .cityTags("thiên nhiên;du lịch biển đảo;lịch sử;văn hóa")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(kiengiang);
        City konTum = City.builder()
                .name("Kon Tum")
                .destinations("Kon Tum;Thành phố Kon Tum;Thác Đắk G'lun;Hang động Đắk Tuar;Làng văn hóa Kon K'tu")
                .description("Kon Tum là một tỉnh thuộc vùng Tây Nguyên, Việt Nam. Tỉnh được biết đến với những danh lam thắng cảnh thiên nhiên hùng vĩ, những nét văn hóa đặc sắc của đồng bào các dân tộc thiểu số, và là một trong những tỉnh có tiềm năng du lịch của Việt Nam.")
                .cityTags("thiên nhiên;văn hóa;ẩm thực;động thực vật")
                .country("Việt Nam")
                .travelPoint(8.5)
                .build();
        cities.add(konTum);
        City laiChâu = City.builder()
                .name("Lai Châu")
                .destinations("Lai Châu;Thành phố Lai Châu;Cổng trời Ô Quy Hồ;Núi Fansipan;Thác Bạc;Đèo Ô Quy Hồ")
                .description("Lai Châu là một tỉnh miền núi thuộc vùng Tây Bắc Bộ, Việt Nam. Tỉnh được biết đến với những danh lam thắng cảnh thiên nhiên hùng vĩ, những nét văn hóa đặc sắc của đồng bào các dân tộc thiểu số, và là một trong những tỉnh có tiềm năng du lịch của Việt Nam.")
                .cityTags("thiên nhiên;văn hóa;ẩm thực;động thực vật")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(laiChâu);
        City lamDong = City.builder()
                .name("Lâm Đồng")
                .destinations("Lâm Đồng;Đà Lạt;Thung lũng Tình Yêu;Làng Cù Lần;Núi Lang Biang;Công viên quốc gia Bidoup - Núi Bà")
                .description("Lâm Đồng là một tỉnh thuộc vùng Tây Nguyên, Việt Nam. Tỉnh được biết đến với những danh lam thắng cảnh thiên nhiên tuyệt đẹp, những nét văn hóa đặc sắc của đồng bào các dân tộc thiểu số, và là một trong những tỉnh có tiềm năng du lịch của Việt Nam.")
                .cityTags("thiên nhiên;văn hóa;ẩm thực;động thực vật")
                .country("Việt Nam")
                .travelPoint(9.0)
                .build();
        cities.add(lamDong);
        City langSon = City.builder()
                .name("Lạng Sơn")
                .destinations("Lạng Sơn;Núi Mẫu;Thác Bản Giốc;Đền Mẫu Đồng Đăng;Phố cổ Đồng Đăng;Thành nhà Mạc")
                .description("Lạng Sơn là một tỉnh thuộc vùng Đông Bắc Bộ, Việt Nam. Tỉnh được biết đến với những danh lam thắng cảnh thiên nhiên hùng vĩ, những di tích lịch sử văn hóa lâu đời, và là một trong những tỉnh có tiềm năng du lịch của Việt Nam.")
                .cityTags("thiên nhiên;lịch sử;văn hóa;ẩm thực")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(langSon);
        City laoCai = City.builder()
                .name("Lào Cai")
                .destinations("Lào Cai;Sa Pa;Núi Fansipan;Cổng trời Ô Quy Hồ;Đèo Ô Quy Hồ;Thung lũng Mường Hoa")
                .description("Lào Cai là một tỉnh miền núi thuộc vùng Tây Bắc Bộ, Việt Nam. Tỉnh được biết đến với những danh lam thắng cảnh thiên nhiên hùng vĩ, những nét văn hóa đặc sắc của đồng bào các dân tộc thiểu số, và là một trong những tỉnh có tiềm năng du lịch của Việt Nam.")
                .cityTags("thiên nhiên;văn hóa;ẩm thực;động thực vật")
                .country("Việt Nam")
                .travelPoint(8.5)
                .build();
        cities.add(laoCai);
        City namDinh = City.builder()
                .name("Nam Định")
                .destinations("Nam Định;Chùa Keo;Đền Trần;Biển Quất Lâm;Khu du lịch sinh thái biển Quất Lâm")
                .description("Nam Định là một tỉnh thuộc vùng đồng bằng sông Hồng, Việt Nam. Tỉnh có diện tích lớn thứ 11 trong 63 tỉnh thành, với dân số hơn 2,2 triệu người. Nam Định được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên đa dạng, di tích lịch sử văn hóa lâu đời đến ẩm thực phong phú.")
                .cityTags("thiên nhiên;lịch sử;văn hóa;ẩm thực")
                .country("Việt Nam")
                .travelPoint(7.5)
                .build();
        cities.add(namDinh);
        City longAn = City.builder()
                .name("Long An")
                .destinations("Long An;Khu du lịch sinh thái Đồng Tháp Mười;Cần Giờ;Nhà trăm cột Cần Đước;Vàm Cỏ Đông")
                .description("Long An là một tỉnh thuộc vùng Đồng bằng sông Cửu Long, Việt Nam. Tỉnh có diện tích lớn nhất vùng Tây Nam Bộ và lớn thứ hai ở Nam Bộ. Long An được biết đến với nhiều điều thú vị, từ vị trí thuận lợi, đồng bằng phì nhiêu đến hệ thống sông ngòi dày đặc.")
                .cityTags("thiên nhiên;du lịch;lịch sử;ẩm thực")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(longAn);
        City ninhBinh = City.builder()
                .name("Ninh Bình")
                .destinations("Ninh Bình;Tam Cốc - Bích Động;Núi non Tràng An;Cố đô Hoa Lư;Thành nhà Hồ;Đền thờ Đinh Tiên Hoàng")
                .description("Ninh Bình là một tỉnh thuộc vùng Bắc Trung Bộ, Việt Nam. Tỉnh có diện tích lớn thứ 3 trong 63 tỉnh thành, với dân số hơn 1,2 triệu người. Ninh Bình được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên hùng vĩ, di tích lịch sử văn hóa lâu đời đến ẩm thực phong phú.")
                .cityTags("thiên nhiên;lịch sử;văn hóa;ẩm thực")
                .country("Việt Nam")
                .travelPoint(8.5)
                .build();
        cities.add(ninhBinh);
        City ninhThuan = City.builder()
                .name("Ninh Thuận")
                .destinations("Ninh Thuận;Vườn quốc gia Núi Chúa;Bãi biển Ninh Chữ;Bãi biển Bình Tiên;Đảo Bình Hưng;Đồi cát Nam Cương")
                .description("Ninh Thuận là một tỉnh thuộc vùng duyên hải Nam Trung Bộ, Việt Nam. Tỉnh có diện tích lớn thứ 10 trong 63 tỉnh thành, với dân số hơn 1,1 triệu người. Ninh Thuận được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên tươi đẹp, những bãi biển hoang sơ đến ẩm thực độc đáo.")
                .cityTags("thiên nhiên;du lịch;ẩm thực")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(ninhThuan);
        City phuTho = City.builder()
                .name("Phú Thọ")
                .destinations("Phú Thọ;Đền Hùng;Vườn quốc gia Xuân Sơn;Hồ Đầm Chài;Núi Cấm Sơn;Làng cổ Hùng Lô")
                .description("Phú Thọ là một tỉnh thuộc vùng Đông Bắc Bộ, Việt Nam. Tỉnh có diện tích lớn thứ 21 trong 63 tỉnh thành, với dân số hơn 1,6 triệu người. Phú Thọ được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên hùng vĩ, di tích lịch sử văn hóa lâu đời đến ẩm thực phong phú.")
                .cityTags("thiên nhiên;lịch sử;văn hóa;ẩm thực")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(phuTho);
        City phuYen = City.builder()
                .name("Phú Yên")
                .destinations("Phú Yên;Gành Đá Đĩa;Bãi Xép;Bãi Tiên Yên;Vịnh Xuân Đài;Chùa Thanh Lương")
                .description("Phú Yên là một tỉnh ven biển thuộc vùng duyên hải Nam Trung Bộ, Việt Nam. Tỉnh có diện tích lớn thứ 5 trong 63 tỉnh thành, với dân số hơn 1,3 triệu người. Phú Yên được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên tươi đẹp, những bãi biển hoang sơ đến ẩm thực độc đáo.")
                .cityTags("thiên nhiên;du lịch;ẩm thực")
                .country("Việt Nam")
                .travelPoint(8.5)
                .build();
        cities.add(phuYen);
        City quangNam = City.builder()
                .name("Quảng Nam")
                .destinations("Quảng Nam;Phố cổ Hội An;Khu di tích đền tháp Mỹ Sơn;Bãi biển Cửa Đại;Ngũ Hành Sơn;Thành cổ Quảng Nam")
                .description("Quảng Nam là một tỉnh ven biển thuộc vùng duyên hải Nam Trung Bộ, Việt Nam. Tỉnh có diện tích lớn thứ 4 trong 63 tỉnh thành, với dân số hơn 1,8 triệu người. Quảng Nam được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên hùng vĩ, di tích lịch sử văn hóa lâu đời đến ẩm thực phong phú.")
                .cityTags("thiên nhiên;lịch sử;văn hóa;ẩm thực")
                .country("Việt Nam")
                .travelPoint(9.0)
                .build();
        cities.add(quangNam);
        City quangNgai = City.builder()
                .name("Quảng Ngãi")
                .destinations("Quảng Ngãi;Bãi biển Mỹ Khê;Đảo Lý Sơn;Núi Thiên Ấn;Suối Chí Linh;Chùa Ông Thơ")
                .description("Quảng Ngãi là một tỉnh ven biển thuộc vùng duyên hải Nam Trung Bộ, Việt Nam. Tỉnh có diện tích lớn thứ 7 trong 63 tỉnh thành, với dân số hơn 1,3 triệu người. Quảng Ngãi được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên tươi đẹp, những bãi biển hoang sơ đến ẩm thực độc đáo.")
                .cityTags("thiên nhiên;du lịch;ẩm thực")
                .country("Việt Nam")
                .travelPoint(8.5)
                .build();
        cities.add(quangNgai);
        City quangNinh = City.builder()
                .name("Quảng Ninh")
                .destinations("Quảng Ninh;Vịnh Hạ Long;Cố đô Hoa Lư;Thành nhà Hồ;Núi Yên Tử;Bãi Cháy")
                .description("Quảng Ninh là một tỉnh ven biển thuộc vùng Đông Bắc Bộ, Việt Nam. Tỉnh có diện tích lớn thứ 3 trong 63 tỉnh thành, với dân số hơn 1,4 triệu người. Quảng Ninh được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên hùng vĩ, di tích lịch sử văn hóa lâu đời đến ẩm thực phong phú.")
                .cityTags("thiên nhiên;lịch sử;văn hóa;ẩm thực")
                .country("Việt Nam")
                .travelPoint(9.0)
                .build();
        cities.add(quangNinh);
        City socTrang = City.builder()
                .name("Sóc Trăng")
                .destinations("Sóc Trăng;Chùa Dơi;Chùa Chén Kiểu;Làng nghề truyền thống;Các khu du lịch sinh thái")
                .description("Sóc Trăng là một tỉnh ven biển thuộc vùng đồng bằng sông Cửu Long, Việt Nam. Tỉnh có diện tích lớn thứ 13 trong 63 tỉnh thành, với dân số hơn 1,2 triệu người. Sóc Trăng được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên tươi đẹp, những nét văn hóa đặc sắc của đồng bào Khmer đến ẩm thực phong phú.")
                .cityTags("thiên nhiên;văn hóa;ẩm thực")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(socTrang);
        City sonLa = City.builder()
                .name("Sơn La")
                .destinations("Sơn La;Mộc Châu;Núi Pha Luông;Thung lũng Mai Châu;Chợ phiên")
                .description("Sơn La là một tỉnh miền núi thuộc vùng Tây Bắc Bộ, Việt Nam. Tỉnh có diện tích lớn thứ 4 trong 63 tỉnh thành, với dân số hơn 1,2 triệu người. Sơn La được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên hùng vĩ, những nét văn hóa đặc sắc của các dân tộc thiểu số đến ẩm thực độc đáo.")
                .cityTags("thiên nhiên;văn hóa;ẩm thực")
                .country("Việt Nam")
                .travelPoint(8.5)
                .build();
        cities.add(sonLa);
        City tayNinh = City.builder()
                .name("Tây Ninh")
                .destinations("Tây Ninh;Chùa Bà Đen;Núi Bà Đen;Thành phố Tây Ninh;Vườn quốc gia Lò Gò - Xa Mát")
                .description("Tây Ninh là một tỉnh thuộc vùng Đông Nam Bộ, Việt Nam. Tỉnh có diện tích lớn thứ 12 trong 63 tỉnh thành, với dân số hơn 1,4 triệu người. Tây Ninh được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên hùng vĩ, những di tích lịch sử văn hóa lâu đời đến ẩm thực phong phú.")
                .cityTags("thiên nhiên;lịch sử;văn hóa;ẩm thực")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(tayNinh);
        City thaiBinh = City.builder()
                .name("Thái Bình")
                .destinations("Thái Bình;Đền thờ Trần Hưng Đạo;Cồn Vành;Khu du lịch sinh thái Cồn Đen;Chợ Viềng")
                .description("Thái Bình là một tỉnh thuộc vùng đồng bằng sông Hồng, Việt Nam. Tỉnh có diện tích lớn thứ 10 trong 63 tỉnh thành, với dân số hơn 2,2 triệu người. Thái Bình được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên tươi đẹp, những di tích lịch sử văn hóa lâu đời đến ẩm thực phong phú.")
                .cityTags("thiên nhiên;lịch sử;văn hóa;ẩm thực")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(thaiBinh);
        City thaiNguyên = City.builder()
                .name("Thái Nguyên")
                .destinations("Thái Nguyên;Đền Hùng;Vườn quốc gia Tam Đảo;Khu di tích lịch sử ATK Định Hóa;Khu du lịch hồ Núi Cốc")
                .description("Thái Nguyên là một tỉnh thuộc vùng trung du và miền núi phía Bắc, Việt Nam. Tỉnh có diện tích lớn thứ 2 trong 63 tỉnh thành, với dân số hơn 1,8 triệu người. Thái Nguyên được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên hùng vĩ, những di tích lịch sử văn hóa lâu đời đến ẩm thực phong phú.")
                .cityTags("thiên nhiên;lịch sử;văn hóa;ẩm thực")
                .country("Việt Nam")
                .travelPoint(8.5)
                .build();
        cities.add(thaiNguyên);
        City thanhHoa = City.builder()
                .name("Thanh Hóa")
                .destinations("Thanh Hóa;Biển Sầm Sơn;Vườn quốc gia Bến En;Khu di tích lịch sử Lam Kinh;Khu du lịch sinh thái Pù Luông")
                .description("Thanh Hóa là một tỉnh thuộc vùng Bắc Trung Bộ, Việt Nam. Tỉnh có diện tích lớn thứ 5 trong 63 tỉnh thành, với dân số hơn 4,2 triệu người. Thanh Hóa được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên hùng vĩ, những di tích lịch sử văn hóa lâu đời đến ẩm thực phong phú.")
                .cityTags("thiên nhiên;lịch sử;văn hóa;ẩm thực")
                .country("Việt Nam")
                .travelPoint(9.0)
                .build();
        cities.add(thanhHoa);
        City tienGiang = City.builder()
                .name("Tiền Giang")
                .destinations("Tiền Giang;Chợ nổi Cái Bè;Cù lao Thới Sơn;Khu du lịch sinh thái Cù lao Tân Phong;Khu di tích lịch sử Gò Tháp")
                .description("Tiền Giang là một tỉnh thuộc vùng đồng bằng sông Cửu Long, Việt Nam. Tỉnh có diện tích lớn thứ 11 trong 63 tỉnh thành, với dân số hơn 1,7 triệu người. Tiền Giang được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên tươi đẹp, những di tích lịch sử văn hóa lâu đời đến ẩm thực phong phú.")
                .cityTags("thiên nhiên;lịch sử;văn hóa;ẩm thực")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(tienGiang);
        City traVinh = City.builder()
                .name("Trà Vinh")
                .destinations("Trà Vinh;Chùa Khmer;Chợ nổi Cái Răng;Khu du lịch sinh thái Lan Vương;Khu di tích lịch sử Vàm Nao")
                .description("Trà Vinh là một tỉnh thuộc vùng đồng bằng sông Cửu Long, Việt Nam. Tỉnh có diện tích lớn thứ 23 trong 63 tỉnh thành, với dân số hơn 1,2 triệu người. Trà Vinh được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên tươi đẹp, những di tích lịch sử văn hóa lâu đời đến ẩm thực phong phú.")
                .cityTags("thiên nhiên;lịch sử;văn hóa;ẩm thực")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(traVinh);
        City tuyenQuang = City.builder()
                .name("Tuyên Quang")
                .destinations("Tuyên Quang;Núi Pắc Tạ;Khu di tích lịch sử Tân Trào;Khu du lịch sinh thái Na Hang;Khu du lịch sinh thái Lâm Bình")
                .description("Tuyên Quang là một tỉnh thuộc vùng Đông Bắc Bộ, Việt Nam. Tỉnh có diện tích lớn thứ 20 trong 63 tỉnh thành, với dân số hơn 760.000 người. Tuyên Quang được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên hùng vĩ, những di tích lịch sử văn hóa lâu đời đến ẩm thực phong phú.")
                .cityTags("thiên nhiên;lịch sử;văn hóa;ẩm thực")
                .country("Việt Nam")
                .travelPoint(8.5)
                .build();
        cities.add(tuyenQuang);
        City vinhLong = City.builder()
                .name("Vĩnh Long")
                .destinations("Vĩnh Long;Chợ nổi Cái Răng;Vườn quốc gia Tràm Chim;Khu du lịch sinh thái Làng lụa Phong Điền;Khu di tích lịch sử chùa Long An")
                .description("Vĩnh Long là một tỉnh thuộc vùng đồng bằng sông Cửu Long, Việt Nam. Tỉnh có diện tích lớn thứ 12 trong 63 tỉnh thành, với dân số hơn 1,3 triệu người. Vĩnh Long được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên tươi đẹp, những di tích lịch sử văn hóa lâu đời đến ẩm thực phong phú.")
                .cityTags("thiên nhiên;lịch sử;văn hóa;ẩm thực")
                .country("Việt Nam")
                .travelPoint(8.0)
                .build();
        cities.add(vinhLong);
        City vinhPhuc = City.builder()
                .name("Vĩnh Phúc")
                .destinations("Vĩnh Phúc;Tam Đảo;Vườn quốc gia Tam Đảo;Khu di tích lịch sử Đền Hùng;Khu du lịch sinh thái Hồ Đại Lải")
                .description("Vĩnh Phúc là một tỉnh thuộc vùng Đông Bắc Bộ, Việt Nam. Tỉnh có diện tích lớn thứ 22 trong 63 tỉnh thành, với dân số hơn 1,1 triệu người. Vĩnh Phúc được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên tươi đẹp, những di tích lịch sử văn hóa lâu đời đến ẩm thực phong phú.")
                .cityTags("thiên nhiên;lịch sử;văn hóa;ẩm thực")
                .country("Việt Nam")
                .travelPoint(8.5)
                .build();
        cities.add(vinhPhuc);
        City yenBai = City.builder()
                .name("Yên Bái")
                .destinations("Yên Bái;Mù Cang Chải;Thung lũng Tú Lệ;Khu di tích lịch sử Văn Miếu Nghĩa Lộ;Khu du lịch sinh thái Tam Đảo")
                .description("Yên Bái là một tỉnh thuộc vùng Tây Bắc Bộ, Việt Nam. Tỉnh có diện tích lớn thứ 27 trong 63 tỉnh thành, với dân số hơn 800.000 người. Yên Bái được biết đến với nhiều điều thú vị, từ cảnh quan thiên nhiên hùng vĩ, những di tích lịch sử văn hóa lâu đời đến ẩm thực phong phú.")
                .cityTags("thiên nhiên;lịch sử;văn hóa;ẩm thực")
                .country("Việt Nam")
                .travelPoint(8.5)
                .build();
        cities.add(yenBai);

        cityRepository.saveAll(cities);
    }
}
