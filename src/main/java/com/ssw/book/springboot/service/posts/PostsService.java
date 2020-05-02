package com.ssw.book.springboot.service.posts;

import com.ssw.book.springboot.domain.posts.Posts;
import com.ssw.book.springboot.domain.posts.PostsRepository;
import com.ssw.book.springboot.web.dto.PostsListResponseDto;
import com.ssw.book.springboot.web.dto.PostsResponseDto;
import com.ssw.book.springboot.web.dto.PostsSaveRequestDto;
import com.ssw.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    /**
     * @Transactional(readOnly=true) 옵션 사용시 트랜잭션 범위는 유지하되, 조회기능만 남아 조회 속도가 개선된다.
     * 등록, 수정, 삭제 기능이 전혀 없는 서비스 메소드에서 사용하는 것을 추천.
     */
    @Transactional
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
        //return postsRepository.findAllDesc().stream().map(posts->new PostsListResponseDto(posts)).collect(Collectors.toList());
        /**
         * postsRepository 결과로 넘어온 Posts의 Stream을 map을 통해 PostsListResponseDto 변환 -> List로 변환하는 메소드다.
         */
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        /**
         * JpaRepository에서 이미 delete 메소드를 지원하고 있음.
         * 엔티티를 파라미터로 삭제할 수도 있고, deleteById 메소드를 이용하면 id로 삭제 가능
         * 존재하는 Posts인지 확인을 위해 엔티티 조회 후 그대로 삭제함.
         */
        postsRepository.delete(posts);
    }

}
